# The Smithing_Smith's_Shop

# Define our values
id = 123
name = "Smithing Smith's Shop"
items = {
  # item id => item amount,
  1321 => 10,
  2347 => 10,
  7141 => 999999999,
  1323 => 10,
  7140 => 999999999,
  1329 => 10,
  7142 => 999999999,
  1325 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)