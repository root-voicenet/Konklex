# Contains the tzar fight pits minigame.
 
require 'java'
java_import 'org.apollo.game.minigame.Minigame'
java_import 'org.apollo.game.event.impl.ConfigEvent'
 
$pits = nil

# Attribute 1 starts game
# Attribute 2 champion update
# Attribute 3 size update
# Attribute 4 time update
 
class Pits < Minigame

  attr_reader :champion, :cooldown

  def initialize
    super("Fight pits")
    @champion = "Nobody"
    @cooldown = 0
  end

  def before_pulse
    tick = @cooldown
    if tick != 0 and tick % 60 == 0
      set_attribute 4, true
    end
  end
 
  def pulse
    before_pulse
    if get_attribute 1
      if get_characters(GAME).size == 1
        @cooldown = 180
        winner = get_characters(GAME).get(0)
        @champion = winner.name
        transfer_team winner, LOBBY
        winner.send ConfigEvent.new(560, 0)
        winner.inventory.add 6529, get_characters(LOBBY).size
        set_attribute 1, false
        set_attribute 2, true
      end
    else
      lobby = get_characters(LOBBY)
      if lobby.size > 5 and cooldown == 0 then
        lobby.each do |character|
          transfer_team(character, GAME)
        end
        set_attribute 1, true
      end
    end
    if get_attribute 2
      get_characters(LOBBY, GAME).each do |character|
        character.send SetInterfaceTextEvent.new(2805, "Current Champion: #{champion}")
      end
      set_attribute 2, false
    end
    if get_attribute 3
      size = get_characters(GAME).size
      if size > 0 then
        get_characters(LOBBY, GAME).each do |character|
          character.send ConfigEvent.new(560, size)
        end
      end
      set_attribute 3, false
    end
    if get_attribute 4
      time = get_time
      if time != 0 then
        get_characters(LOBBY).each do |character|
          character.send_message "Next round starts in #{time} minute#{time == 1 ? "" : "s"}!"
        end
      end
      set_attribute 4, false
    end
    if cooldown > 0
      @cooldown -= 1
    end
  end

  def get_champion
    return @champion
  end

  def get_time
    return (@cooldown / 60)
  end
 
end
 
$pits = Pits.new
$pits.add_listener PitListener.new
World.get_world.register $pits