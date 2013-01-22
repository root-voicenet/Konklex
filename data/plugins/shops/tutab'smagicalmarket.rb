# The Tutab's_Magical_Market

# Define our values
id = 145
name = "Tutab's Magical Market"
items = {
  # item id => item amount,
  556 => 1000,
  557 => 1000,
  4008 => 10,
  554 => 1000,
  563 => 100,
  4006 => 10,
  4023 => 10,
  555 => 1000,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)