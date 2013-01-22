# The Gulluck_and_Sons

# Define our values
id = 67
name = "Gulluck and Sons"
items = {
  # item id => item amount,
  882 => 1000,
  877 => 300,
  880 => 0,
  841 => 10,
  839 => 10,
  837 => 10,
  39 => 100,
  40 => 100,
  41 => 100,
  42 => 100,
  1349 => 100,
  1353 => 100,
  1363 => 100,
  1365 => 100,
  1369 => 100,
  1307 => 100,
  1309 => 100,
  1311 => 100,
  1313 => 100,
  1315 => 100,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)