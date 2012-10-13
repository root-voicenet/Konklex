# The Dargaud's_Bow_and_Arrows

# Define our values
id = 142
name = "Dargaud's Bow and Arrows"
items = {
  # item id => item amount,
  882 => 1000,
  884 => 300,
  886 => 300,
  888 => 100,
  890 => 100,
  892 => 100,
  4773 => 0,
  4778 => 0,
  4783 => 0,
  4793 => 0,
  4798 => 0,
  4803 => 0,
  52 => 1000,
  39 => 300,
  40 => 300,
  41 => 100,
  42 => 100,
  43 => 100,
  44 => 30,
  841 => 10,
  839 => 10,
  843 => 10,
  845 => 10,
  849 => 10,
  847 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)