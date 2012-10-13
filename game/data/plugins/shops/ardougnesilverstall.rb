# The Ardougne_Silver_Stall

# Define our values
id = 24
name = "Ardougne Silver Stall"
items = {
  # item id => item amount,
  1714 => 10,
  442 => 0,
  2355 => 0,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)