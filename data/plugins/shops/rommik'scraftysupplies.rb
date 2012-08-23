# The Rommik's_Crafty_Supplies

# Define our values
id = 101
name = "Rommik's Crafty Supplies"
items = {
  # item id => item amount,
  1755 => 10,
  1592 => 10,
  1597 => 10,
  1595 => 10,
  1733 => 10,
  1734 => 1000,
  1599 => 10,
  2976 => 10,
  5523 => 10,
  9434 => 10,
  11065 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)