# The Arhein's_Store

# Define our values
id = 61
name = "Arhein's Store"
items = {
  # item id => item amount,
  1925 => 30,
  1265 => 10,
  1923 => 10,
  1887 => 10,
  590 => 10,
  1755 => 10,
  2347 => 10,
  954 => 10,
  1931 => 10,
  946 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)