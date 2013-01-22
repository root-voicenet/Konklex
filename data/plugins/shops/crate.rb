# The Crate

# Define our values
id = 9
name = "Crate"
items = {
  # item id => item amount,
  2518 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)