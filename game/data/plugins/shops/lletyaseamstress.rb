# The Lletya_Seamstress

# Define our values
id = 131
name = "Lletya Seamstress"
items = {
  # item id => item amount,
  1759 => 100,
  1767 => 10,
  1771 => 10,
  1733 => 10,
  1769 => 10,
  1773 => 10,
  1763 => 10,
  1734 => 1000,
  1765 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)