require 'java'
java_import 'org.apollo.game.model.Animation'

CHISEL = 1755
CUT_GEM_ANIMATION = Animation.new(885)

def find_slot(id)
  uncut_ids = [1623, 1621, 1619, 1617, 1631, 6571,1625,1627,1629]
  for i in 0..uncut_ids.length
    if(uncut_ids[i] == id) 
      return i;
    end
  end
  
  
  return nil
end

def makegem(character, item, slot)
  cut_ids = [1607, 1605, 1603, 1601, 1615, 6573,1609,1611,1613]
  level_req = [20, 27, 34, 43, 55, 67, 1, 13, 16]
  exp_rate = [50, 67, 85, 107, 137, 167, 15, 20, 25]
  skills = character.skill_set
  crafting_level = skills.get_skill(Skill::CRAFTING).maximum_level
  

  
  if(crafting_level < level_req[slot])
    character.send_message "You need a crafting level of #{level_req[slot]} to cut this gem"
    return
  end
  
    character.inventory.remove(item)
    character.play_animation CUT_GEM_ANIMATION
    if character.inventory.add cut_ids[slot]
        character.send_message "You carefully cut this gem"
        skills.add_experience Skill::CRAFTING, exp_rate[slot]
     end
end

on :event, :ItemOnItem do |ctx, player, event|
  
  itemId = event.get_id;
  usedId = event.get_target_id;
  
  if find_slot(itemId) != nil && usedOnItem == CHISEL
    makegem(player, itemId, find_slot(itemId))
  end
  
  if itemId == CHISEL && find_slot(usedOnItem) != nil
    makegem(player, usedOnItem, find_slot(usedOnItem))
  end
  
end

