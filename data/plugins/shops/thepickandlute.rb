# The The_Pick_and_Lute

# Define our values
id = 56
name = "The Pick and Lute"
items = {
  # item id => item amount,
  1917 => 10,
  1907 => 10,
  1913 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)