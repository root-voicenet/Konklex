# The Ifaba's_General_Store

# Define our values
id = 143
name = "Ifaba's General Store"
items = {
  # item id => item amount,
  1925 => 30,
  1931 => 30,
  2347 => 10,
  1935 => 10,
  954 => 10,
  590 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)