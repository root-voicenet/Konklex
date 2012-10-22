# The Aubury's_Rune_Shop

# Define our values
id = 6
name = "Aubury's Rune Shop"
items = {
  # item id => item amount,
  554 => 300,
  555 => 300,
  556 => 300,
  557 => 300,
  558 => 100,
  559 => 100,
  562 => 30,
  560 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)