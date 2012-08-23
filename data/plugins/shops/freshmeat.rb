# The Fresh_Meat

# Define our values
id = 81
name = "Fresh Meat"
items = {
  # item id => item amount,
  2140 => 10,
  2878 => 0,
  7228 => 0,
  2142 => 10,
  3228 => 10,
  2136 => 300,
  9986 => 0,
  2132 => 300,
  9978 => 10,
  2138 => 10,
  2876 => 0,
  7566 => 0,
  2337 => 0,
  3226 => 100,
  9988 => 0,
  9980 => 0,
  9992 => 0,
  9984 => 0,
  7230 => 0,
  7224 => 0,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)