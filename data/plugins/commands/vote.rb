require 'java'
java_import 'org.apollo.game.model.Item'
java_import 'org.apollo.game.model.inter.store.ShopPaymentType'

VOTES = {}

class VotePayment < ShopPaymentType
  def buyItem(player, item)
    points = player.voting_points
    value = buyValue item.item.id
    puts "has #{points} and value #{value}"
    if points >= value
      player.set_voting_points points-value
      return true
    end
    return false
  end

  def buyValue(item)
    case item
      when 1038, 1040, 1042, 1044, 1046, 1048
        return 500
      when 2673, 2669, 2671, 2675, 2665, 2661, 2663, 2667, 2657, 2653, 2655, 2659
        return 25
      when 2587, 2583, 2585, 2589, 2595, 2591, 2593, 2597
        return 15
      when 2633, 2635, 2637
        return 50
      when 1037
        return 400
      when 962
        return 450
      when 981
        return 70
      when 1050
        return 500
    end
    return 0
  end

  def getName
    return "vote points"
  end

  def sellItem(player, item)
    return false
  end

  def sellValue(item)
    return 0
  end
end

on :command, :_vote, RIGHTS_OWNER do |player, command|
  args = command.arguments
  if args.length == 2
    name = args[0]
    id = args[1].to_i

    recieve = World.world.player name
    if recieve != nil
      count = VOTES[recieve.name]
      if count != nil
        VOTES[recieve.name] += 1
      else
        VOTES[recieve.name] = 1
      end
    else
      player.send_message "That player is currently offline."
    end
  else
    player.send_message "Syntax: _vote [player] [id]"
  end
end

on :command, :vote do |player, command|
  count = VOTES[player.name]
  if count != nil and count > 0
    VOTES[player.name] = 0
    player.inventory.add 995, count * 50000
    player.send_message "Thanks for supporting us!"

    # Lets add some vote points so they can buy stuff
    points = player.vote_points
    player.set_vote_points points+count
  end
end

# Define our values
id = 201
name = "Voting Rewards"
items = {
  # Phats
  1038 => 1,
  1040 => 1,
  1042 => 1,
  1044 => 1,
  1046 => 1,
  1048 => 1,
  1037 => 1,
  # Misc rares
  962 => 1,
  981 => 1,
  1050 => 1,
  # Guthix
  2673 => 1,
  2669 => 1,
  2671 => 1,
  2675 => 1,
  # Zamorak
  2657 => 1,
  2653 => 1,
  2655 => 1,
  2659 => 1,
  # Sara
  2665 => 1,
  2661 => 1,
  2663 => 1,
  2667 => 1,
  # Black t
  2587 => 1,
  2583 => 1,
  2585 => 1,
  2589 => 1,
  # Black g
  2595 => 1,
  2591 => 1,
  2593 => 1,
  2597 => 1,
  # Beret
  2633 => 1,
  2635 => 1,
  2637 => 1,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)
shop.set_payment VotePayment.new
