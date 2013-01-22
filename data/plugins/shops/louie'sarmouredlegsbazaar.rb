# The Louie's_Armoured_Legs_Bazaar

# Define our values
id = 37
name = "Louie's Armoured Legs Bazaar"
items = {
  # item id => item amount,
  1075 => 10,
  1067 => 10,
  1069 => 10,
  1077 => 10,
  1071 => 10,
  1073 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)