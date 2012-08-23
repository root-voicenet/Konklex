# The Diango's_Toy_Store

# Define our values
id = 98
name = "Diango's Toy Store"
items = {
  # item id => item amount,
  2520 => 10,
  2522 => 10,
  2524 => 10,
  2526 => 10,
  4613 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)