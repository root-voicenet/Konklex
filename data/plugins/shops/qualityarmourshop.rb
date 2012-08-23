# The Quality_Armour_Shop

# Define our values
id = 156
name = "Quality Armour Shop"
items = {
  # item id => item amount,
  1107 => 10,
  1105 => 10,
  1109 => 10,
  1195 => 10,
  1177 => 10,
  1141 => 10,
  1143 => 10,
  1145 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)