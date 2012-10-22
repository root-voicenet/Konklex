# The Thessalia's_Clothing

# Define our values
id = 2
name = "Thessalia's Clothing"
items = {
  # item id => item amount,
  1005 => 10,
  1129 => 10,
  1059 => 10,
  1061 => 10,
  1757 => 10,
  1013 => 10,
  1015 => 10,
  1011 => 10,
  1007 => 10,
  950 => 10,
  428 => 10,
  426 => 10,
  1733 => 10,
  1734 => 1000,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)