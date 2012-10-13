# The Ardougne_Gem_Stall

# Define our values
id = 25
name = "Ardougne Gem Stall"
items = {
  # item id => item amount,
  1607 => 0,
  1605 => 0,
  1603 => 0,
  1601 => 0,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)