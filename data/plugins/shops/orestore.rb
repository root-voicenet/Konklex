# The Ore_Store

# Define our values
id = 88
name = "Ore Store"
items = {
  # item id => item amount,
  436 => 10,
  438 => 10,
  440 => 0,
  442 => 0,
  453 => 0,
  444 => 0,
  447 => 0,
  449 => 0,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)