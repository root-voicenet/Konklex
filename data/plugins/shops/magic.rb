
# The magic store.
require 'java'
java_import 'org.apollo.game.model.inter.store.Shop'

# Define our values.
id = 2
name = "Magic Shop"
items = { 
  # item id => item amount,
  554 => 10000, 555 => 10000, 556 => 10000, 557 => 10000, 558 => 10000, 559 => 10000, 560 => 10000, 561 => 10000, 562 => 10000, 563 => 10000, 564 => 10000, 565 => 10000, 566 => 10000, 1381 => 10, 1381 => 10, 1385 => 10, 1387 => 10, 2415 => 100, 2416 => 100, 2417 => 100, 14499 => 100, 14501 => 100, 14997 => 100, 7937 => 100
}
type = Shop::ShopType::UNLIMITED_BUY_ONLY

# Ship out the shop to the world.
shop = appendShop(Shops.new(id, name, items, type))