# The Legends'_Guild_Shop_of_Useful_Items

# Define our values
id = 41
name = "Legends' Guild Shop of Useful Items"
items = {
  # item id => item amount,
  299 => 500,
  1590 => 10,
  1542 => 10,
  2366 => 10,
  1052 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)