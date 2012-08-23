# The Khazard_General_Store

# Define our values
id = 109
name = "Khazard General Store"
items = {
  # item id => item amount,
  1265 => 10,
  1931 => 30,
  1935 => 10,
  1735 => 10,
  1925 => 30,
  590 => 10,
  1755 => 10,
  2347 => 10,
  954 => 10,
  1933 => 500,
  583 => 10,
  1941 => 1000,
  946 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)