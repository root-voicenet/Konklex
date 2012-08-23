# The Aurel's_Supplies

# Define our values
id = 108
name = "Aurel's Supplies"
items = {
  # item id => item amount,
  1351 => 10,
  590 => 10,
  3363 => 10,
  353 => 10,
  952 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)