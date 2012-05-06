require 'java'

# Credits arrowzftw for gemcrafting outline xD
# Site: http://runescape.salmoneus.net/tips/battlestaves.html

BATTLESTAVES = 1391

def find_slot(id)
  orb_ids = [571, 575, 569, 573] # The orbs you use on the staff
  for i in 0..orb_ids.length
    if(orb_ids[i] == id)
      return i;
    end
  end
  return nil
end

def makestave(character, item, usedOn, slot)
  stave_ids = [1395, 1399, 1393, 1397] # Finished product
  level_req = [54, 58, 62, 66] # Level you will need
  exp_rate = [100, 112, 125, 137] # The experience granted per staff
  skills = character.skill_set
  crafting_level = skills.get_skill(Skill::CRAFTING).maximum_level
  if(crafting_level < level_req[slot])
    character.send_message "You need a crafting level of #{level_req[slot]} to create this staff"
    return
  end
  character.inventory.remove(item)
  character.inventory.remove(usedOn)
  if character.inventory.add stave_ids[slot]
    character.send_message "You put the staff together."
    skills.add_experience Skill::CRAFTING, exp_rate[slot]
  end
end

on :event, :ItemOnItem do |ctx, player, event|
  itemId = event.get_id
  usedId = event.get_target_id
  if find_slot(itemId) != nil && usedId == BATTLESTAVES
    makestave(player, itemId, usedId, find_slot(itemId))
  end
  if itemId == BATTLESTAVES && find_slot(usedId) != nil
    makestave(player, itemId, usedId, find_slot(usedId))
  end
end
  

