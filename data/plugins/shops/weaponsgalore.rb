# The Weapons_Galore

# Define our values
id = 90
name = "Weapons Galore"
items = {
  # item id => item amount,
  1299 => 10,
  1343 => 10,
  1369 => 10,
  3099 => 10,
  1315 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)