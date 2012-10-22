# The Quartermaster's_Stores

# Define our values
id = 137
name = "Quartermaster's Stores"
items = {
  # item id => item amount,
  3200 => 10,
  3196 => 10,
  2309 => 10,
  3190 => 10,
  3204 => 10,
  1931 => 30,
  3192 => 10,
  1935 => 10,
  3198 => 10,
  3202 => 10,
  1735 => 10,
  3194 => 10,
  590 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)