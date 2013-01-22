# The Dodgy_Mike's_Second_Hand_Clothing

# Define our values
id = 119
name = "Dodgy Mike's Second Hand Clothing"
items = {
  # item id => item amount,
  7130 => 10,
  7136 => 10,
  7124 => 10,
  7112 => 10,
  7114 => 10,
  7132 => 10,
  7138 => 10,
  7116 => 10,
  7126 => 10,
  7128 => 10,
  7134 => 10,
  7110 => 10,
  7122 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)