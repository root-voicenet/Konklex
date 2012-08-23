# The TzHaar-Hur-Tel's_Equipment_Store

# Define our values
id = 113
name = "TzHaar-Hur-Tel's Equipment Store"
items = {
  # item id => item amount,
  6522 => 10,
  6523 => 10,
  6524 => 10,
  6525 => 10,
  6526 => 10,
  6527 => 10,
  6528 => 10,
  6568 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)