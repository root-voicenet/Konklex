# The Brian's_Battleaxe_Bazaar

# Define our values
id = 33
name = "Brian's Battleaxe Bazaar"
items = {
  # item id => item amount,
  1375 => 10,
  1363 => 10,
  1365 => 10,
  1367 => 10,
  1369 => 10,
  1371 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)