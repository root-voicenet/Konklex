# The Shantay_Pass_Shop

# Define our values
id = 71
name = "Shantay Pass Shop"
items = {
  # item id => item amount,
  1823 => 30,
  1831 => 30,
  1937 => 10,
  1921 => 10,
  1929 => 10,
  946 => 10,
  1833 => 10,
  1835 => 10,
  1837 => 10,
  2349 => 0,
  314 => 1000,
  2347 => 10,
  1925 => 30,
  1923 => 10,
  1935 => 10,
  1854 => 300,
  954 => 100,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)