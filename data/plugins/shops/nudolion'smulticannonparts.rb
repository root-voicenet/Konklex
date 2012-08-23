# The Nudolion's_Multicannon_Parts

# Define our values
id = 94
name = "Nudolion's Multicannon Parts"
items = {
  # item id => item amount,
  6 => 10,
  8 => 10,
  10 => 10,
  12 => 10,
  5 => 10,
  4 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)