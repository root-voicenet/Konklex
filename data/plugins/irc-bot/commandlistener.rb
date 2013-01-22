class CommandListener
  def execute(pirc, command)
  end
end

class PrivilegeCommandListener < CommandListener
  def execute(pirc, command)
    users = pirc.get_users command.channel
    users.each do |nick|
      if nick.nick.downcase == command.sender.downcase
        if nick.is_op or command.sender == "Buroa"
          execute_privilege pirc, command
        end
        break
      end
    end
  end

  def execute_privilege(pirc, command)
  end
end

class StaffCommandListener < CommandListener
  AUTHD = {}

  def execute(pirc, command)
    auth = AUTHD[command.sender]
    if auth != nil
      if command.command.name == ".logout"
        AUTHD[command.sender] = nil
        pirc.send_message command.sender, "You have been logged out successfully."
      else
        execute_staff pirc, command
      end
    elsif command.command.name == ".auth"
      if command.command.arguments.length == 1
        pass = command.command.arguments[0]
        if pass == "test"
          AUTHD[command.sender] = true
          pirc.send_message command.sender, "You are now authenticated4 #{command.sender}."
        else
          AUTHD.each do |key, value|
            pirc.send_message key, "#{command.sender} has failed authentication."
          end
        end
      end
    end
  end

  def execute_staff(pirc, command)
  end
end