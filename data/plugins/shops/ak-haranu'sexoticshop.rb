# The Ak-Haranu's_Exotic_Shop

# Define our values
id = 106
name = "Ak-Haranu's Exotic Shop"
items = {
  # item id => item amount,
  4740 => 100,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)