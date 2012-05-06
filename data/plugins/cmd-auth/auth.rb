require 'java'
java_import 'org.apollo.util.MysqlUtil'
java_import 'java.sql.ResultSet'
java_import 'java.sql.SQLException'

on :command, :auth do |player, command|
  args = command.arguments
  if args.length == 1
    if verify(args[0], player)
      player.send_message "Your token was verified successfully."
      MysqlUtil.query("DELETE FROM crsps_konklex.tokens WHERE token = '#{args[0]}'")
    else
      player.send_message "There was a error verifying your token."
    end
  else
    player.send_message "Syntax: ::auth key"
  end
end

def verify(token, player)
  begin
    rs = MysqlUtil.query("SELECT * FROM crsps_konklex.tokens WHERE token = '#{token}'")
    while rs.next
      if rs.get_int("id") == 1
        return TokenUtil.redeemVotePackage rs.get_int("package"), player
      elsif rs.get_int("id") == 2
        return TokenUtil.redeemPaymentPackage rs.get_int("package"), player
      end
    end
  rescue SQLException=>e
    # Do nothing
  end
  return false
end