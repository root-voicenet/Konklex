# The Happy_Heros'_H'emporium

# Define our values
id = 53
name = "Happy Heros' H'emporium"
items = {
  # item id => item amount,
  1377 => 10,
  1434 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)