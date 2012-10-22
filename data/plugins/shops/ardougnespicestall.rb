# The Ardougne_Spice_Stall

# Define our values
id = 27
name = "Ardougne Spice Stall"
items = {
  # item id => item amount,
  2007 => 10,
  946 => 10,
  1550 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)