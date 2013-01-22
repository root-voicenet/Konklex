# The Varrock_Sword_Shop

# Define our values
id = 4
name = "Varrock Sword Shop"
items = {
  # item id => item amount,
  1205 => 100,
  1203 => 100,
  1207 => 100,
  1209 => 100,
  1211 => 100,
  1277 => 100,
  1279 => 100,
  1281 => 100,
  1283 => 100,
  1285 => 100,
  1287 => 100,
  1291 => 100,
  1293 => 100,
  1295 => 100,
  1297 => 100,
  1299 => 100,
  1301 => 100,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)