# The Trader_Stan's_Trading_Post

# Define our values
id = 107
name = "Trader Stan's Trading Post"
items = {
  # item id => item amount,
  1931 => 30,
  1935 => 10,
  1735 => 10,
  1925 => 30,
  1923 => 10,
  1887 => 10,
  590 => 10,
  1755 => 10,
  2347 => 10,
  550 => 10,
  9003 => 10,
  954 => 10,
  946 => 10,
  2114 => 0,
  1963 => 10,
  2108 => 0,
  1785 => 10,
  1783 => 0,
  401 => 0,
  592 => 0,
  301 => 10,
  307 => 10,
  1941 => 10,
  9629 => 10,
  3226 => 10,
  1025 => 10,
  6014 => 0,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)