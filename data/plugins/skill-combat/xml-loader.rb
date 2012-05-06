require 'java'
java_import 'javax.xml.parsers.DocumentBuilder'
java_import 'javax.xml.parsers.DocumentBuilderFactory'
java_import 'org.w3c.dom.Document'
java_import 'org.w3c.dom.Element'
java_import 'org.w3c.dom.NodeList'

class Load
  
  attr_reader :factory, :builder, :doc, :list, :length
  
  def initialize
    init
    start
  end
  
  def init
    @factory = DocumentBuilderFactory.new_instance
    @builder = factory.new_document_builder
  end
  
  def start
    loadPlayerWeapons
    loadNpcAnimations
  end
  
  def loadPlayerWeapons
    doc = builder.parse java.io.File.new("./data/plugins/skill-combat/data/player-weapons.xml")
    list = doc.get_elements_by_tag_name "weapon"
    length = list.get_length
    doc.get_document_element.normalize
    for i in 0..length
      element = list.item i
      if element != nil
        weapon = element.getAttribute("id").to_i
        anim = element.getAttribute("anim").to_i
        delay = element.getAttribute("delay").to_i
        Combat.addPlayerAnimation weapon, anim
        Combat.addWeaponDelay weapon, delay
      end
    end
  end
  
  def loadNpcAnimations
    doc = builder.parse java.io.File.new("./data/plugins/skill-combat/data/npc-animation.xml")
    list = doc.get_elements_by_tag_name "animation"
    length = list.get_length
    doc.get_document_element.normalize
    for i in 0..length
      element = list.item i
      if element != nil
        npc = element.getAttribute("npc").to_i
        death = element.getAttribute("death").to_i
        attack = element.getAttribute("attack").to_i
        Combat.addNpcAttackAnimation npc, attack
        Combat.addNpcDeathAnimation npc, death
      end
    end
  end
  
end

Load.new