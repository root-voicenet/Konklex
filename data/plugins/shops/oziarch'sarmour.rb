# The Oziarch's_Armour

# Define our values
id = 69
name = "Oziarch's Armour"
items = {
  # item id => item amount,
  1127 => 10,
  1135 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)