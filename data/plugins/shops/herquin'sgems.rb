# The Herquin's_Gems

# Define our values
id = 46
name = "Herquin's Gems"
items = {
  # item id => item amount,
  1623 => 0,
  1621 => 0,
  1619 => 0,
  1617 => 0,
  1607 => 0,
  1605 => 0,
  1603 => 0,
  1601 => 0,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)