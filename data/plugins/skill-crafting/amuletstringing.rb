require 'java'

# Amulet stringing plugin pretty simple to add to if you need to
# TODO: The silver data stuff

WOOL = 1759

def find_slot(id)
  amulet_ids = [1673, 1675, 1677, 1679, 1681, 1683, 6579] # The amulet you use on the wool
  for i in 0..amulet_ids.length
    if(amulet_ids[i] == id)
      return i;
    end
  end
  return nil
end

def stringamulet(character, item, usedOn, slot)
  finished_product = [1692, 1694, 1696, 1698, 1700, 1702, 6581] # The finished product Id
  level_required = [8, 24, 31, 50, 70, 80, 90] # The level needed for stringing an amulet
  experience_rate = [30, 65, 70, 85, 100, 150, 165] # The experience gained
  skills = character.skill_set
  crafting_level = skills.get_skill(Skill::CRAFTING).maximum_level
  if(crafting_level < level_required[slot])
    character.send_message "You need a crafting level of #{level_required[slot]} to string this amulet"
    return
  end
  character.inventory.remove item
  character.inventory.remove usedOn
  if character.inventory.add finished_product[slot]
    character.send_message "You attach the wool to the amulet."
    skills.add_experience Skill::CRAFTING, experience_rate[slot]
  end
end

on :event, :item_on_item do |ctx, player, event|
  itemId = event.id
  usedId = event.target_id
  if find_slot itemId != nil && usedId == WOOL
    stringamulet player, itemId, usedId, find_slot(itemId)
  end
  if itemId == WOOL && find_slot(usedId) != nil
    stringamulet player, itemId, usedId, find_slot(usedId)
  end
end