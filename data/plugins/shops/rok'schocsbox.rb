# The Rok's_Chocs_Box

# Define our values
id = 72
name = "Rok's Chocs Box"
items = {
  # item id => item amount,
  6794 => 10,
  1973 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)