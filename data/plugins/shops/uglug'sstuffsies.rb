# The Uglug's_Stuffsies

# Define our values
id = 77
name = "Uglug's Stuffsies"
items = {
  # item id => item amount,
  4844 => 10,
  10927 => 0,
  1777 => 0,
  2876 => 0,
  2878 => 10,
  4850 => 0,
  946 => 10,
  4773 => 0,
  4778 => 0,
  4783 => 0,
  4788 => 0,
  4793 => 0,
  4798 => 0,
  4803 => 0,
  4827 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)