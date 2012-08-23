# The Raetul_and_Co's_Cloth_Store

# Define our values
id = 127
name = "Raetul and Co's Cloth Store"
items = {
  # item id => item amount,
  1837 => 10,
  1835 => 10,
  1833 => 10,
  4684 => 10,
  1733 => 10,
  950 => 10,
  1734 => 1000,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)