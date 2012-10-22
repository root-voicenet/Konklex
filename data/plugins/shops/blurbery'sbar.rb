# The Blurbery's_Bar

# Define our values
id = 63
name = "Blurbery's Bar"
items = {
  # item id => item amount,
  2028 => 10,
  2030 => 10,
  2032 => 10,
  2034 => 10,
  2036 => 10,
  2038 => 10,
  2040 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)