# The Legends_Guild_General_Store

# Define our values
id = 40
name = "Legends Guild General Store"
items = {
  # item id => item amount,
  373 => 10,
  2323 => 10,
  121 => 10,
  886 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)