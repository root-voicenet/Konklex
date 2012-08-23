# The Pollnivneach_General_Store

# Define our values
id = 70
name = "Pollnivneach General Store"
items = {
  # item id => item amount,
  1931 => 30,
  1935 => 10,
  1823 => 30,
  1833 => 10,
  1837 => 10,
  1925 => 10,
  4593 => 10,
  4591 => 10,
  1985 => 10,
  2120 => 10,
  1982 => 10,
  1937 => 10,
  1921 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)