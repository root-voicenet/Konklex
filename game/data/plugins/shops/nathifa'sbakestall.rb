# The Nathifa's_Bake_Stall

# Define our values
id = 128
name = "Nathifa's Bake Stall"
items = {
  # item id => item amount,
  2309 => 10,
  1891 => 10,
  1901 => 10,
  1823 => 30,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)