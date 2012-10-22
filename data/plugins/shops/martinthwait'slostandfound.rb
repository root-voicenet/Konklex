# The Martin_Thwait's_Lost_and_Found

# Define our values
id = 57
name = "Martin Thwait's Lost and Found"
items = {
  # item id => item amount,
  954 => 10,
  1523 => 10,
  1755 => 10,
  946 => 10,
  5560 => 10,
  864 => 100,
  863 => 100,
  865 => 100,
  3095 => 100,
  3096 => 100,
  3097 => 100,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)