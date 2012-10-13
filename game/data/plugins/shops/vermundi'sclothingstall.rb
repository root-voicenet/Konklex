# The Vermundi's_Clothing_Stall

# Define our values
id = 153
name = "Vermundi's Clothing Stall"
items = {
  # item id => item amount,
  5030 => 10,
  5042 => 10,
  950 => 10,
  5048 => 10,
  5036 => 10,
  5024 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)