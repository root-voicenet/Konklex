# The Blades_By_Urbi

# Define our values
id = 125
name = "Blades By Urbi"
items = {
  # item id => item amount,
  1211 => 10,
  1217 => 0,
  1205 => 10,
  1321 => 10,
  1215 => 10,
  1203 => 10,
  1323 => 10,
  1209 => 10,
  1213 => 10,
  1207 => 10,
  1325 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)