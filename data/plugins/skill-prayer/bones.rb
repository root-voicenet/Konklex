require 'java'
java_import 'org.apollo.game.model.Animation'

BURY_ANIMATION = Animation.new(827)

BONES = [526,2530,528,530,2859,3179,3180,3181,3182,3183,3185,3186,3187,3125,4812,3127,3123,532,534,6812,536,4830,4832,4834,6729]
RATE = [5,5,5,14,14,14,14,14,14,14,14,14,14,15,23,25,25,45,30,50,72,84,96,140,125]

def bury_bone(player, id, slot)
  player.inventory.remove id, 1
  player.skill_set.add_experience Skill::PRAYER, RATE[slot]
  player.send_message "You bury the bones."
  player.play_animation BURY_ANIMATION
end

def look(player, item)
  for i in 0..BONES.length
    if BONES[i] == item
      bury_bone(player, item, i)
      break
    end
  end
end

on :event, :first_item_option do |ctx, player, event|
  look(player, event.get_id)
end
