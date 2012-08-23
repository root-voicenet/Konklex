# The Jukat

# Define our values
id = 139
name = "Jukat"
items = {
  # item id => item amount,
  1215 => 30,
  1305 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)