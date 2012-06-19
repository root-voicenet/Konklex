LOGOUT_BUTTON_ID = 2458

on :button, LOGOUT_BUTTON_ID do |player|
  player.logout
end

on :event, :player_idle do |ctx, player, event|
  if not player.is_members then player.logout end
end