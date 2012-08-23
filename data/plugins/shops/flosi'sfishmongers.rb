# The Flosi's_Fishmongers

# Define our values
id = 92
name = "Flosi's Fishmongers"
items = {
  # item id => item amount,
  377 => 0,
  359 => 0,
  331 => 0,
  341 => 0,
  371 => 0,
  383 => 0,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)