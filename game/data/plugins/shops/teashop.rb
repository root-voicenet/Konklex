# The Tea_Shop

# Define our values
id = 5
name = "Tea Shop"
items = {
  # item id => item amount,
  1978 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)