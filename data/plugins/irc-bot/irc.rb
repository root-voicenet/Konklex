# Load default configurations
$pirc = IrcListener.new
$dispatcher = ChatDispatcher.new $pirc

# Register commands
$dispatcher.register ".players", PlayersCommandListener.new
$dispatcher.register ".time", TimeCommandListener.new
$dispatcher.register ".part", PartCommandListener.new
$dispatcher.register ".stats", StatsCommandListener.new
$dispatcher.register ".uptime", UptimeCommandListener.new
$dispatcher.register ".cmb", CombatCommandListener.new
$dispatcher.register ".skill", SkillCommandListener.new
$dispatcher.register ".yell", YellCommandListener.new
$dispatcher.register ".price", PriceCommandListener.new
$dispatcher.register ".help", HelpCommandListener.new
$dispatcher.register ".voice", VoiceCommandListener.new
$dispatcher.register ".hop", HalfOpCommandListener.new
$dispatcher.register ".op", OpCommandListener.new
$dispatcher.register ".cmd", CmdCommandListener.new
$dispatcher.register ".auth", AuthCommandListener.new
$dispatcher.register ".logout", AuthCommandListener.new
$dispatcher.register ".mass", MassMessageCommandListener.new
$dispatcher.register ".kick", KickCommandListener.new
$dispatcher.register ".topic", TopicCommandListener.new
$dispatcher.register ".invite", InviteCommandListener.new
$dispatcher.register ".raw", RawCommandListener.new
$dispatcher.register ".debug", DebugCommandListener.new

# Set some settings
$pirc.set_message_delay 0
$pirc.set_name "KServ"
$pirc.set_login "KServ"
$pirc.set_version "Konklex Services"

# Fire up the bot
$pirc.connect "[2001:41d0:2:e05::6667]", 8080
$pirc.set_mode $pirc.nick, "+BT"
