# The Keldagrim's_Best_Bread

# Define our values
id = 149
name = "Keldagrim's Best Bread"
items = {
  # item id => item amount,
  2309 => 10,
  1891 => 10,
  1901 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)