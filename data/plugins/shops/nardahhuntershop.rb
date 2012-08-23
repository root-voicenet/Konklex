# The Nardah_Hunter_Shop

# Define our values
id = 73
name = "Nardah Hunter Shop"
items = {
  # item id => item amount,
  10010 => 10,
  10012 => 300,
  10025 => 300,
  10150 => 10,
  10006 => 10,
  10008 => 10,
  10029 => 10,
  596 => 10,
  10031 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)