# The Richard's_Farming_Shop

# Define our values
id = 39
name = "Richard's Farming Shop"
items = {
  # item id => item amount,
  5341 => 10,
  5343 => 10,
  1735 => 10,
  952 => 10,
  5325 => 10,
  5331 => 10,
  5354 => 100,
  6032 => 100,
  6032 => 300,
  1925 => 100,
  5418 => 10,
  5376 => 10,
  1942 => 0,
  1957 => 0,
  1965 => 0,
  1982 => 0,
  5986 => 0,
  5504 => 0,
  5982 => 0,
  5994 => 0,
  5996 => 0,
  5998 => 0,
  6000 => 0,
  6002 => 0,
  5931 => 0,
  6006 => 0,
  6036 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)