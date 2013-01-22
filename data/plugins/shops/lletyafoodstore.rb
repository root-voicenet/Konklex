# The Lletya_Food_Store

# Define our values
id = 132
name = "Lletya Food Store"
items = {
  # item id => item amount,
  2309 => 10,
  1891 => 10,
  1985 => 10,
  1993 => 10,
  379 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)