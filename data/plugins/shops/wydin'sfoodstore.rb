# The Wydin's_Food_Store

# Define our values
id = 30
name = "Wydin's Food Store"
items = {
  # item id => item amount,
  1933 => 500,
  2132 => 10,
  2138 => 10,
  1965 => 10,
  1963 => 0,
  1951 => 0,
  2309 => 0,
  1973 => 0,
  1985 => 0,
  1982 => 10,
  1942 => 10,
  1550 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)