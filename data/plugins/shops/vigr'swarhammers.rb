# The Vigr's_Warhammers

# Define our values
id = 155
name = "Vigr's Warhammers"
items = {
  # item id => item amount,
  1337 => 10,
  1335 => 10,
  1339 => 10,
  1341 => 10,
  1343 => 10,
  1345 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)