# The Betty's_Magic_Emporium

# Define our values
id = 34
name = "Betty's Magic Emporium"
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
  221 => 300,
  579 => 10,
  1017 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)