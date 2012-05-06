require 'java'
java_import 'org.apollo.game.model.inter.minigame.util.Team'

CWOBJECT = {}

class CWObject

 attr_reader :id, :team, :required

  def initialize(id, team, required)
    @id = id
    @team = team
    @required = required
  end

end

def append_object(obj)
  CWOBJECT[obj.id] = obj
end

append_object CWObject.new(4406, Team::SARADOMIN, true) ## SARA PORTAL ACTIVE LEAVE
append_object CWObject.new(6280, Team::SARADOMIN, true) ## SARA STAIRS LOBBY ACTIVE
append_object CWObject.new(4469, Team::SARADOMIN, true) ## SARA ENERGY BARRIER
append_object CWObject.new(4458, Team::SARADOMIN, true) ## SARA BANDAGES
append_object CWObject.new(4387, 0, false) ## SARA PORTAL
append_object CWObject.new(4389, 0, false) ## SARA WAIT PORTAL LEAVE
append_object CWObject.new(4388, 0, false) ## ZAMORAK PORTAL
append_object CWObject.new(4390, 0, false) ## ZAMORAK WAIT PORTAL LEAVE
append_object CWObject.new(4408, 0, false) ## GUTHIX PORTAL
append_object CWObject.new(4902, Team::ZAMORAK, true) ## SARA BANNER
append_object CWObject.new(4903, Team::SARADOMIN, true) ## SARA BANNER
append_object CWObject.new(4378, Team::ZAMORAK, true) ## ZAMORAK BANNER GONE
append_object CWObject.new(4377, Team::SARADOMIN, true) ## SARA BANNER