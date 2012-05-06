# This class holds the world shop adding.
# Specify the payment type by executing shop.set_payment(payment)
# while; payment extends org.apollo.game.model.inter.store.ShopPaymentType

require 'java'
java_import 'org.apollo.game.model.inter.store.Shop'
java_import 'org.apollo.game.model.Item'
java_import 'org.apollo.game.model.World'

class Shops

 attr_reader :id, :name, :items, :type

  def initialize(id, name, items, type)
    @id = id
    @name = name
    @items = items
    @type = type
  end

end

def appendShop(shop)
  worldshop = Shop.new(shop.name, shop.type)
  World.get_world.get_stores.addShop(shop.id, worldshop)
  shop.items.each do |item, amount|
    worldshop.add_item Item.new(item, amount)
  end
  return worldshop
end