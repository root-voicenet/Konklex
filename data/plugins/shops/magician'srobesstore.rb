# The Magician's_Robes_Store

# Define our values
id = 14
name = "Magician's Robes Store"
items = {
  # item id => item amount,
  4089 => 100,
  4091 => 100,
  4093 => 100,
  4095 => 100,
  4097 => 100,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)