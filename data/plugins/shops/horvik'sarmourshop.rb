# The Horvik's_Armour_Shop

# Define our values
id = 8
name = "Horvik's Armour Shop"
items = {
  # item id => item amount,
  1103 => 10,
  1101 => 10,
  1155 => 100,
  1153 => 100,
  1117 => 100,
  1115 => 100,
  1173 => 10,
  1175 => 10,
  1075 => 100,
  1067 => 100,
  1087 => 10,
  1081 => 10,
  1119 => 100,
  1125 => 10,
  1121 => 100,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)