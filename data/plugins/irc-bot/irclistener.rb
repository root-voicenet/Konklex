require 'java'
Dir["*.jar"].each { |jar| require jar }

java_import 'org.jibble.pircbot.IrcException'
java_import 'org.jibble.pircbot.PircBot'

class IrcListener < PircBot
  def onMessage(channel, sender, login, hostname, message)
    cmd = ChatCommand.new sender, channel, message
    $dispatcher.dispatch cmd
  end

  def onPrivateMessage(sender, login, hostname, message)
    cmd = ChatCommand.new sender, sender, message
    $dispatcher.dispatch cmd
  end

  def onJoin(channel, sender, login, hostname)
    if sender == "Buroa"
      send_message channel, "Welcome my Owner,4 #{sender}"
    elsif channel == "#konklex"
      if sender != nick
        voice channel, sender
      end
    end
  end

  def onInvite(targetNick, sourceNick, sourceLogin, sourceHostname, channel)
    join_channel channel
  end
end