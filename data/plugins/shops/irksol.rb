# The Irksol

# Define our values
id = 140
name = "Irksol"
items = {
  # item id => item amount,
  1641 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)