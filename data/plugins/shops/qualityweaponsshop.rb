# The Quality_Weapons_Shop

# Define our values
id = 157
name = "Quality Weapons Shop"
items = {
  # item id => item amount,
  888 => 100,
  890 => 100,
  1295 => 10,
  1365 => 10,
  1325 => 10,
  1297 => 10,
  1369 => 10,
  1285 => 10,
  1287 => 10,
  1303 => 10,
  851 => 10,
  853 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)