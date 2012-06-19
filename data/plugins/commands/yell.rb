require 'java'
java_import 'java.lang.StringBuilder'
java_import 'org.apollo.game.model.Config'
java_import 'org.apollo.game.model.World'

on :command, :yell do |player, command|
  args = command.arguments
  if args.length > 0
    if Config.SERVER_YELL
      message = extract(command)
      World.get_world.get_player_repository.each do |players|
        players.send_message(build(player) << message)
      end
    end
  else
    player.send_message "Syntax: ::yell [message]"
  end
end

def extract(command)
  builder = StringBuilder.new()
  command.get_arguments.each do |message|
    builder.append(message << " ")
  end
  return builder.to_string
end

def build(player)
  level = player.get_privilege_level.to_integer
  case level
    when 0
      return player.is_members ? "[Donor] #{player.get_name}: " : "[Member] #{player.get_name}: "
    else
      rights = player.get_privilege_level.to_string.downcase
      first = rights.split('')[0].upcase
      return "[" << (first << rights[1, rights.length]) << "] #{player.get_name}: "
  end
end