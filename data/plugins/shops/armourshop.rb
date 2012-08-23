# The Armour_Shop

# Define our values
id = 89
name = "Armour Shop"
items = {
  # item id => item amount,
  1109 => 10,
  1143 => 10,
  1159 => 10,
  1181 => 10,
  1197 => 10,
  1071 => 10,
  1085 => 10,
  1121 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)