# The Gift_Shop

# Define our values
id = 80
name = "Gift Shop"
items = {
  # item id => item amount,
  1351 => 10,
  1925 => 30,
  2878 => 10,
  1931 => 30,
  314 => 1000,
  2347 => 10,
  946 => 10,
  2866 => 100,
  954 => 10,
  590 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)