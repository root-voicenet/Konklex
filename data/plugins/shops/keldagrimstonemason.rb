# The Keldagrim_Stonemason

# Define our values
id = 158
name = "Keldagrim Stonemason"
items = {
  # item id => item amount,
  8784 => 30,
  3420 => 500,
  8788 => 30,
  8786 => 30,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)