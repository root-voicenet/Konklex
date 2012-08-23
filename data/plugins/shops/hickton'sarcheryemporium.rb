# The Hickton's_Archery_Emporium

# Define our values
id = 59
name = "Hickton's Archery Emporium"
items = {
  # item id => item amount,
  877 => 300,
  882 => 1000,
  884 => 1000,
  886 => 0,
  888 => 0,
  890 => 0,
  892 => 0,
  4773 => 0,
  4778 => 0,
  4783 => 0,
  4788 => 0,
  4793 => 0,
  4798 => 0,
  4803 => 0,
  39 => 100,
  40 => 100,
  41 => 100,
  42 => 100,
  43 => 10,
  44 => 10,
  841 => 10,
  839 => 10,
  837 => 10,
  843 => 10,
  845 => 10,
  4827 => 10,
  1133 => 10,
  1097 => 10,
  9785 => 1,
  9783 => 1,
  9784 => 1,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)