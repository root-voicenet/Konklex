# The Rasolo_the_Wandering_Merchant

# Define our values
id = 62
name = "Rasolo the Wandering Merchant"
items = {
  # item id => item amount,
  1969 => 10,
  2023 => 10,
  740 => 10,
  1215 => 30,
  550 => 10,
  1925 => 10,
  1941 => 10,
  273 => 10,
  970 => 10,
  975 => 10,
  1599 => 10,
  2976 => 10,
  1823 => 30,
  1837 => 10,
  1854 => 300,
  2524 => 10,
  3377 => 10,
  2894 => 10,
  1909 => 10,
  3787 => 10,
  3711 => 10,
  3678 => 10,
  3424 => 10,
  3420 => 10,
  5 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)