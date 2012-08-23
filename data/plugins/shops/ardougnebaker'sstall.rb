# The Ardougne_Baker's_Stall

# Define our values
id = 23
name = "Ardougne Baker's Stall"
items = {
  # item id => item amount,
  2309 => 10,
  1891 => 10,
  1901 => 10,
  1973 => 100,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)