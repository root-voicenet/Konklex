# The Bandit_Bargains

# Define our values
id = 42
name = "Bandit Bargains"
items = {
  # item id => item amount,
  1823 => 30,
  1831 => 30,
  1937 => 10,
  1921 => 10,
  1929 => 10,
  1935 => 10,
  1923 => 10,
  1925 => 30,
  1837 => 10,
  1833 => 10,
  1835 => 10,
  946 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)