# The Beefy_Bill's_Supplies

# Define our values
id = 135
name = "Beefy Bill's Supplies"
items = {
  # item id => item amount,
  882 => 30,
  1173 => 10,
  1277 => 10,
  1929 => 10,
  1887 => 10,
  1949 => 10,
  1931 => 30,
  1937 => 10,
  2313 => 10,
  1951 => 10,
  1735 => 10,
  841 => 10,
  1969 => 10,
  590 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)