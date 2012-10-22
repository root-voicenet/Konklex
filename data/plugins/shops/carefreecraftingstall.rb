# The Carefree_Crafting_Stall

# Define our values
id = 152
name = "Carefree Crafting Stall"
items = {
  # item id => item amount,
  1759 => 10,
  1755 => 10,
  1597 => 10,
  1733 => 10,
  1592 => 10,
  1734 => 1000,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)