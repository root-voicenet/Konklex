# The Lowe's_Archery_Emporium

# Define our values
id = 7
name = "Lowe's Archery Emporium"
items = {
  # item id => item amount,
  882 => 1000,
  884 => 100,
  886 => 100,
  888 => 100,
  890 => 30,
  877 => 300,
  841 => 10,
  839 => 10,
  843 => 10,
  845 => 10,
  849 => 10,
  843 => 10,
  849 => 10,
  847 => 10,
  853 => 10,
  851 => 10,
  1129 => 10,
  1059 => 100,
  1061 => 10,
  1095 => 10,
  1133 => 10,
  1097 => 10,
  1131 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)