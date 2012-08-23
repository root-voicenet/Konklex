# The Brian's_Archery_Supplies

# Define our values
id = 100
name = "Brian's Archery Supplies"
items = {
  # item id => item amount,
  886 => 100,
  888 => 30,
  890 => 0,
  843 => 10,
  845 => 10,
  849 => 10,
  847 => 10,
  853 => 10,
  851 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)