# The Lletya_Archery_Shop

# Define our values
id = 133
name = "Lletya Archery Shop"
items = {
  # item id => item amount,
  890 => 100,
  877 => 1000,
  837 => 10,
  884 => 1000,
  888 => 100,
  845 => 10,
  843 => 10,
  892 => 30,
  886 => 300,
  847 => 10,
  849 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)