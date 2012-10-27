require 'java'
java_import 'org.apollo.game.model.inter.store.ShopPaymentType'

class ZombiePayment < ShopPaymentType

  def buyItem(player, item)
    if ($zombie.get_points > 200)
      $zombie.remove_points 200
      return true
    end
    return false
  end

  def buyValue(item)
    return 200
  end

  def getName
    return "zpoints"
  end

  def sellItem(player, item)
    return false
  end

  def sellValue
    return 0
  end

end

# Define our values
id = 200
name = "Zombie Minigame"
items = {
  6199 => 5,
}
type = SHOP_LBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)
shop.set_payment ZombiePayment.new

# Todo
# Implement the mystery box

