package org.apollo.backend.impl;

import org.apollo.backend.codec.FrontendPacket;
import org.apollo.backend.codec.FrontendPacketBuilder;
import org.apollo.backend.method.MethodEncoder;
import org.apollo.backend.method.impl.HiscoreMethod;
import org.apollo.game.model.Player;
import org.apollo.game.model.Skill;
import org.apollo.game.model.SkillSet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An {@link MethodEncoder} for the {@link HiscoreMethod}
 * @author Steve
 */
public final class HiscoreMethodEncoder extends MethodEncoder<HiscoreMethod> {

	@Override
	public FrontendPacket encode(HiscoreMethod method) {
		final FrontendPacketBuilder builder = new FrontendPacketBuilder("getHiscore");
		final Player player = method.getPlayer();
		if (player != null) {
			final SkillSet skills = player.getSkillSet();
			final JSONArray hiscore = new JSONArray();
			for (int i = 0; i < skills.size(); i++) {
				final Skill skill = skills.getSkill(i);
				final JSONObject temp = new JSONObject();
				try {
					temp.put("lvl", skill.getMaximumLevel());
					temp.put("exp", skill.getExperience());
				} catch (JSONException e) {
				}
				hiscore.put(temp);
			}
			builder.addParameter("user", method.getUser());
			builder.addParameter("hiscores", hiscore);
		} else {
			builder.setError(true);
			builder.addParameter("response", "Invalid username.");
		}
		return builder.toFrontendPacket();
	}
}
