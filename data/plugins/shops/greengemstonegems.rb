# The Green_Gemstone_Gems

# Define our values
id = 151
name = "Green Gemstone Gems"
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