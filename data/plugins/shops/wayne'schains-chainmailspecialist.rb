# The Wayne's_Chains_-_Chainmail_Specialist

# Define our values
id = 47
name = "Wayne's Chains - Chainmail Specialist"
items = {
  # item id => item amount,
  1103 => 10,
  1101 => 10,
  1105 => 10,
  1107 => 10,
  1109 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)