# The Bob's_Brilliant_Axes

# Define our values
id = 58
name = "Bob's Brilliant Axes"
items = {
  # item id => item amount,
  1265 => 10,
  1351 => 10,
  1349 => 10,
  1353 => 10,
  1363 => 10,
  1365 => 10,
  1369 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)