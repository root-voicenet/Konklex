package org.apollo.net.release.r317;

import org.apollo.api.method.impl.PrivateChatMethod;
import org.apollo.api.method.impl.SendFriendMethod;
import org.apollo.api.method.impl.SendPlayerMethod;
import org.apollo.api.method.impl.TimeMethod;
import org.apollo.game.event.impl.AnimateObjectEvent;
import org.apollo.game.event.impl.BuildPlayerMenuEvent;
import org.apollo.game.event.impl.CameraResetEvent;
import org.apollo.game.event.impl.CameraShakeEvent;
import org.apollo.game.event.impl.ChatPrivacySettingsEvent;
import org.apollo.game.event.impl.CloseInterfaceEvent;
import org.apollo.game.event.impl.ConfigEvent;
import org.apollo.game.event.impl.ConstructMapRegionEvent;
import org.apollo.game.event.impl.CreateGroundEvent;
import org.apollo.game.event.impl.CreateObjectEvent;
import org.apollo.game.event.impl.DebugMessageEvent;
import org.apollo.game.event.impl.DestroyGroundEvent;
import org.apollo.game.event.impl.DestroyObjectEvent;
import org.apollo.game.event.impl.DisplayTabInterfaceEvent;
import org.apollo.game.event.impl.DisplayWeightEvent;
import org.apollo.game.event.impl.EnterAmountEvent;
import org.apollo.game.event.impl.FlashSideBarEvent;
import org.apollo.game.event.impl.GraphicEvent;
import org.apollo.game.event.impl.HintIconEvent;
import org.apollo.game.event.impl.IdAssignmentEvent;
import org.apollo.game.event.impl.LogoutEvent;
import org.apollo.game.event.impl.MinimapStateEvent;
import org.apollo.game.event.impl.NpcSynchronizationEvent;
import org.apollo.game.event.impl.OpenInterfaceDialogueEvent;
import org.apollo.game.event.impl.OpenInterfaceEvent;
import org.apollo.game.event.impl.OpenInterfaceOverlayEvent;
import org.apollo.game.event.impl.OpenInterfaceSidebarEvent;
import org.apollo.game.event.impl.OpenWelcomeScreenEvent;
import org.apollo.game.event.impl.PlayerSynchronizationEvent;
import org.apollo.game.event.impl.PositionEvent;
import org.apollo.game.event.impl.PrivateChatLoadedEvent;
import org.apollo.game.event.impl.ProjectileEvent;
import org.apollo.game.event.impl.RegionChangeEvent;
import org.apollo.game.event.impl.ResetButtonsEvent;
import org.apollo.game.event.impl.ResetClientEvent;
import org.apollo.game.event.impl.SendFriendEvent;
import org.apollo.game.event.impl.SendIgnoreEvent;
import org.apollo.game.event.impl.SendPrivateChatEvent;
import org.apollo.game.event.impl.ServerMessageEvent;
import org.apollo.game.event.impl.SetInterfaceComponentEvent;
import org.apollo.game.event.impl.SetInterfaceItemModelEvent;
import org.apollo.game.event.impl.SetInterfaceModelMoodEvent;
import org.apollo.game.event.impl.SetInterfaceNpcModelEvent;
import org.apollo.game.event.impl.SetInterfacePlayerModelEvent;
import org.apollo.game.event.impl.SetInterfaceTextEvent;
import org.apollo.game.event.impl.SongEvent;
import org.apollo.game.event.impl.SpecialEvent;
import org.apollo.game.event.impl.SwitchTabInterfaceEvent;
import org.apollo.game.event.impl.SystemUpdateEvent;
import org.apollo.game.event.impl.UpdateItemsEvent;
import org.apollo.game.event.impl.UpdateRunEnergyEvent;
import org.apollo.game.event.impl.UpdateSkillEvent;
import org.apollo.game.event.impl.UpdateSlottedItemsEvent;
import org.apollo.net.meta.PacketMetaDataGroup;
import org.apollo.net.release.Release;

/**
 * An implementation of {@link Release} for the 317 protocol.
 * @author Graham
 */
public final class Release317 extends Release {

	/**
	 * The incoming packet lengths array.
	 */
	public static final int[] PACKET_LENGTHS = { 
		0, 0, 0, 1, -1, 0, 0, 0, 0, 0, // 0-10
		0, 0, 0, 0, 8, 0, 6, 2, 2, 0, // 10
		0, 2, 0, 6, 0, 12, 0, 0, 0, 0, // 20
		0, 0, 0, 0, 0, 8, 4, 0, 0, 2, // 30
		2, 6, 0, 6, 0, -1, 0, 0, 0, 0, // 40
		0, 0, 0, 12, 0, 0, 0, 8, 0, 0, // 50
		8, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 60
		6, 0, 2, 2, 8, 6, 0, -1, 0, 6, // 70
		0, 0, 0, 0, 0, 1, 4, 6, 0, 0, // 80
		0, 0, 0, 0, 0, 3, 0, 0, -1, 0, // 90
		0, 13, 0, -1, 0, 0, 0, 0, 0, 0,// 100
		0, 0, 0, 0, 0, 0, 0, 6, 0, 0, // 110
		1, 0, 6, 0, 0, 0, -1, 0, 2, 6, // 120
		0, 4, 6, 8, 0, 6, 0, 0, 0, 2, // 130
		0, 0, 0, 0, 0, 6, 0, 0, 0, 0, // 140
		0, 0, 1, 2, 0, 2, 6, 0, 0, 0, // 150
		0, 0, 0, 0, -1, -1, 0, 0, 0, 0,// 160
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 170
		0, 8, 0, 3, 0, 2, 0, 0, 8, 1, // 180
		0, 0, 12, 0, 6, 0, 0, 0, 0, 0, // 190
		2, 0, 0, 0, 0, 0, 0, 0, 4, 0, // 200
		4, 0, 0, 0, 7, 8, 0, 0, 10, 0, // 210
		0, 0, 0, 0, 0, 0, -1, 0, 6, 0, // 220
		1, 0, 0, 0, 6, 0, 6, 8, 1, 0, // 230
		0, 4, 0, 0, 0, 0, -1, 0, -1, 4,// 240
		0, 0, 6, 6, 0, 0, // 250
	};
	
	/**
	 * The incoming packet lengths array.
	 */
	public static final int[] API_PACKET_LENGTHS = { 
		0, -1, 17, 1, 10, 2, -1, 0, 0, 0, // 0-10
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 10
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 20
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 30
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 40
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 50
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 60
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 70
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 80
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 90
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,// 100
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 110
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 120
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 130
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 140
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 150
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,// 160
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 170
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 180
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 190
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 200
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 210
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 220
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 230
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,// 240
		0, 0, 0, 0, 0, 0, // 250
	};

	/**
	 * Creates and initializes this release.
	 */
	public Release317() {
		super(317, PacketMetaDataGroup.createFromArray(PACKET_LENGTHS), PacketMetaDataGroup.createFromArray(API_PACKET_LENGTHS));
		init();
	}

	/**
	 * Initializes this release by registering encoders and decoders.
	 */
	private void init() {
		// register decoders
		register(new KeepAliveEventDecoder(), 0);
		register(new CharacterDesignEventDecoder(), 101);
		register(new WalkEventDecoder(), 248, 164, 98);
		register(new ChatEventDecoder(), 4);
		register(new ButtonEventDecoder(), 185);
		register(new CommandEventDecoder(), 103);
		register(new SwitchItemEventDecoder(), 214);
		register(new FirstObjectActionEventDecoder(), 132);
		register(new FirstItemOptionEventDecoder(), 122);
		register(new FirstItemActionEventDecoder(), 145);
		register(new FirstPlayerOptionEventDecoder(), 73);
		register(new SecondPlayerOptionEventDecoder(), 139);
		register(new ThirdPlayerOptionEventDecoder(), 39);
		register(new FourthPlayerOptionEventDecoder(), 153);
		register(new FirstNpcOptionEventDecoder(), 72);
		register(new SecondObjectActionEventDecoder(), 252);
		register(new SecondItemOptionEventDecoder(), 41);
		register(new SecondItemActionEventDecoder(), 117);
		register(new SecondNpcOptionEventDecoder(), 155);
		register(new ThirdObjectActionEventDecoder(), 70);
		register(new ThirdItemOptionEventDecoder(), 16);
		register(new ThirdItemActionEventDecoder(), 43);
		register(new ThirdNpcOptionEventDecoder(), 17);
		register(new FourthNpcOptionEventDecoder(), 21);
		register(new FourthItemActionEventDecoder(), 129);
		register(new FourthItemOptionEventDecoder(), 75);
		register(new FifthItemOptionEventDecoder(), 87);
		register(new FifthItemActionEventDecoder(), 135);
		register(new ClosedInterfaceEventDecoder(), 130);
		register(new EnteredAmountEventDecoder(), 208);
		register(new ItemOnItemDecoder(), 53);
		register(new PickupItemDecoder(), 236);
		register(new FriendsListEventDecoder(), 215, 188, 133, 74);
		register(new PrivateChatEventDecoder(), 126);
		register(new ItemUsedOnObjectDecoder(), 192);
		register(new RegionLoadEventDecoder(), 121);
		register(new MagicOnItemEventDecoder(), 237);
		register(new ChatPrivacySettingsEventDecoder(), 95);
		register(new DialogueContinueEventDecoder(), 40);
		register(new CameraMovementEventDecoder(), 86);
		register(new ClientFocusChangeEventDecoder(), 3);
		register(new MagicOnPlayerEventDecoder(), 249);
		register(new PlayerReportEventDecoder(), 218);
		register(new PlayerIdleEventDecoder(), 202);
		register(new ItemOnPlayerEventDecoder(), 14);
		register(new ItemOnFloorEventDecoder(), 25);
		register(new MagicOnObjectEventDecoder(), 35);
		register(new MagicOnGroundEventDecoder(), 181);
		register(new MagicOnNpcEventDecoder(), 131);
		// register encoders
		register(IdAssignmentEvent.class, new IdAssignmentEventEncoder());
		register(RegionChangeEvent.class, new RegionChangeEventEncoder());
		register(ServerMessageEvent.class, new ServerMessageEventEncoder());
		register(DebugMessageEvent.class, new DebugMessageEventEncoder());
		register(PlayerSynchronizationEvent.class, new PlayerSynchronizationEventEncoder());
		register(NpcSynchronizationEvent.class, new NpcSynchronizationEventEncoder());
		register(OpenInterfaceEvent.class, new OpenInterfaceEventEncoder());
		register(CloseInterfaceEvent.class, new CloseInterfaceEventEncoder());
		register(SwitchTabInterfaceEvent.class, new SwitchTabInterfaceEventEncoder());
		register(OpenInterfaceOverlayEvent.class, new OpenInterfaceOverlayEventEncoder());
		register(LogoutEvent.class, new LogoutEventEncoder());
		register(UpdateItemsEvent.class, new UpdateItemsEventEncoder());
		register(UpdateSlottedItemsEvent.class, new UpdateSlottedItemsEventEncoder());
		register(UpdateSkillEvent.class, new UpdateSkillEventEncoder());
		register(OpenInterfaceSidebarEvent.class, new OpenInterfaceSidebarEventEncoder());
		register(EnterAmountEvent.class, new EnterAmountEventEncoder());
		register(SetInterfaceTextEvent.class, new SetInterfaceTextEventEncoder());
		register(CreateGroundEvent.class, new CreateGroundEventEncoder());
		register(DestroyGroundEvent.class, new DestroyGroundEventEncoder());
		register(CreateObjectEvent.class, new CreateObjectEventEncoder());
		register(DestroyObjectEvent.class, new DestroyObjectEventEncoder());
		register(PositionEvent.class, new PositionEventEncoder());
		register(SongEvent.class, new SongEventEncoder());
		register(SendFriendEvent.class, new SendFriendEventEncoder());
		register(SendIgnoreEvent.class, new SendIgnoreEventEncoder());
		register(SendPrivateChatEvent.class, new SendPrivateChatEventEncoder());
		register(PrivateChatLoadedEvent.class, new PrivateChatLoadedEventEncoder());
		register(ConfigEvent.class, new ConfigEventEncoder());
		register(BuildPlayerMenuEvent.class, new BuildPlayerMenuEventEncoder());
		register(UpdateRunEnergyEvent.class, new UpdateRunEnergyEventEncoder());
		register(SystemUpdateEvent.class, new SystemUpdateEventEncoder());
		register(ResetButtonsEvent.class, new ResetButtonsEventEncoder());
		register(ChatPrivacySettingsEvent.class, new ChatPrivacySettingsEventEncoder());
		register(DisplayTabInterfaceEvent.class, new DisplayTabInterfaceEventEncoder());
		register(SetInterfaceItemModelEvent.class, new SetInterfaceItemModelEventEncoder());
		register(HintIconEvent.class, new HintIconEventEncoder());
		register(SetInterfaceComponentEvent.class, new SetInterfaceComponentEventEncoder());
		register(OpenInterfaceDialogueEvent.class, new OpenInterfaceDialogueEventEncoder());
		register(SetInterfacePlayerModelEvent.class, new SetInterfacePlayerModelEventEncoder());
		register(SetInterfaceNpcModelEvent.class, new SetInterfaceNpcModelEventEncoder());
		register(SetInterfaceModelMoodEvent.class, new SetInterfaceModelMoodEventEncoder());
		register(FlashSideBarEvent.class, new FlashSideBarEventEncoder());
		register(MinimapStateEvent.class, new MinimapStateEventEncoder());
		register(OpenWelcomeScreenEvent.class, new OpenWelcomeScreenEventEncoder());
		register(DisplayWeightEvent.class, new DisplayWeightEventEncoder());
		register(ConstructMapRegionEvent.class, new ConstructMapRegionEventEncoder());
		register(CameraResetEvent.class, new CameraResetEventEncoder());
		register(CameraShakeEvent.class, new CameraShakeEventEncoder());
		register(ProjectileEvent.class, new ProjectileEventEncoder());
		register(ResetClientEvent.class, new ResetClientEventEncoder());
		register(GraphicEvent.class, new GraphicEventEncoder());
		register(SpecialEvent.class, new SpecialEventEncoder());
		register(AnimateObjectEvent.class, new AnimateObjectEventEncoder());
		// register api decoders
		register(new PrivateChatMethodDecoder(), 1);
		register(new ReceiveFriendMethodDecoder(), 2);
		register(new LabelWorldMethodDecoder(), 3);
		register(new ReceivePlayerMethodDecoder(), 4);
		register(new UpdateMethodDecoder(), 5);
		register(new PlayerCommandMethodDecoder(), 6);
		// register api encoders
		register(SendFriendMethod.class, new SendFriendMethodEncoder());
		register(SendPlayerMethod.class, new SendPlayerMethodEncoder());
		register(PrivateChatMethod.class, new PrivateChatMethodEncoder());
		register(TimeMethod.class, new TimeMethodEncoder());
	}
}
