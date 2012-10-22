# The Neitiznot_Supplies

# Define our values
id = 93
name = "Neitiznot Supplies"
items = {
  # item id => item amount,
  946 => 10,
  2347 => 10,
  1734 => 1000,
  1733 => 10,
  4819 => 10,
  1351 => 10,
  1759 => 100,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)