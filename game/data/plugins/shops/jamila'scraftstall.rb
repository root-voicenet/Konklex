# The Jamila's_Craft_Stall

# Define our values
id = 129
name = "Jamila's Craft Stall"
items = {
  # item id => item amount,
  1595 => 10,
  11065 => 10,
  1755 => 10,
  1599 => 10,
  1597 => 10,
  1733 => 10,
  1592 => 10,
  2976 => 10,
  1734 => 1000,
  5523 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)