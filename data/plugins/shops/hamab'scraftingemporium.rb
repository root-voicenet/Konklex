# The Hamab's_Crafting_Emporium

# Define our values
id = 146
name = "Hamab's Crafting Emporium"
items = {
  # item id => item amount,
  1759 => 100,
  11065 => 10,
  1755 => 10,
  4020 => 10,
  1597 => 10,
  1733 => 10,
  1592 => 10,
  1734 => 1000,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)