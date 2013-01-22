require 'java'
java_import 'org.apollo.game.model.skill.farming.Farming'

on :event, :object_action do |ctx, player, event|
  pos = event.get_position
  if event.get_option == 1
    result = false
    if player.get_compost != nil
      result = player.get_compost.handle_object_click event.get_id, pos.get_x, pos.get_y
    end
    if not result
      if Farming.harvest player, pos.get_x, pos.get_y
        ctx.break_handler_chain
      end
    else
      ctx.break_handler_chain
    end
  elsif event.get_option == 2
    if Farming.inspect_object player, pos.get_x, pos.get_y
      ctx.break_handler_chain
    end
  end
end

on :event, :item_used_on_object do |ctx, player, event|
  pos = event.get_position
  result = Farming.prepare_crop player, event.get_id, event.get_object, pos.get_x, pos.get_y
  if result
    ctx.break_handler_chain
  end
end

on :event, :item_on_item do |ctx, player, event|
  primary = event.id
  secondary = event.target_id
  primary_slot = event.get_slot
  secondary_slot = event.get_target_slot

  result = player.get_seedling.water_seedling primary, secondary, primary_slot, secondary_slot
  if result
    ctx.break_handler_chain
  else
    result = player.get_seedling.place_seed_in_pot primary, secondary, primary_slot, secondary_slot
    if result
      ctx.break_handler_chain
    end
  end
end
