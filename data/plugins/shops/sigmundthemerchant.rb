# The Sigmund_the_Merchant

# Define our values
id = 84
name = "Sigmund the Merchant"
items = {
  # item id => item amount,
  590 => 10,
  954 => 10,
  233 => 10,
  1931 => 30,
  2142 => 10,
  2309 => 10,
  952 => 10,
  36 => 10,
  1755 => 10,
  2347 => 10,
  229 => 10,
  227 => 10,
  1925 => 30,
  1929 => 10,
  1944 => 10,
  1942 => 10,
  1965 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)