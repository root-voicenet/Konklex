require 'java'
java_import 'org.apollo.backend.codec.FrontendPacketReader'
java_import 'org.apollo.game.scheduling.impl.SystemUpdateTask'

on :http, :config do |ctx, channel, method|
  if method.key == "abcdef"
    id = method.id
    state = method.state
    if id == 0
      if state == 0
        org.apollo.game.model.Config.SERVER_WHITELIST = false
      elsif state == 1
        org.apollo.game.model.Config.SERVER_WHITELIST = true
      end
    elsif id == 1
      if state == 1
        SystemUpdateTask.start
      end
    elsif id == 2
      if state == 0
        org.apollo.game.model.Config.SERVER_NPCS = false
      elsif state == 1
        org.apollo.game.model.Config.SERVER_NPCS = true
      end
    elsif id == 3
      if state == 0
        org.apollo.game.model.Config.SERVER_YELL = false
      elsif state == 1
        org.apollo.game.model.Config.SERVER_YELL = true
      end
    elsif id == 4
      if state == 0
        org.apollo.game.model.Config.SERVER_COMMAND_AD = false
      elsif state == 1
        org.apollo.game.model.Config.SERVER_COMMAND_AD = true
      end
    end
  end
  channel.close
end
