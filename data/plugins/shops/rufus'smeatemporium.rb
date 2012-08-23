# The Rufus's_Meat_Emporium

# Define our values
id = 103
name = "Rufus's Meat Emporium"
items = {
  # item id => item amount,
  2132 => 100,
  2138 => 30,
  2134 => 10,
  2136 => 10,
  335 => 0,
  349 => 0,
  331 => 0,
  383 => 0,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)