# The General_Store

# Define our values
id = 1
name = "General Store"
items = {
  # item id => item amount,
  1931 => 30,
  1935 => 10,
  5603 => 10,
  1925 => 30,
  1923 => 10,
  1887 => 10,
  590 => 10,
  2347 => 10,
  550 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)