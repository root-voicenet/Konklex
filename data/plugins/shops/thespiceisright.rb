# The The_Spice_Is_Right

# Define our values
id = 126
name = "The Spice Is Right"
items = {
  # item id => item amount,
  175 => 10,
  5970 => 0,
  1931 => 30,
  2169 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)