# The Rovar's_Bar

# Define our values
id = 102
name = "Rovar's Bar"
items = {
  # item id => item amount,
  2955 => 999999999,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)