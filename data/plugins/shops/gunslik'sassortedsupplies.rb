# The Gunslik's_Assorted_Supplies

# Define our values
id = 159
name = "Gunslik's Assorted Supplies"
items = {
  # item id => item amount,
  1925 => 30,
  36 => 10,
  973 => 10,
  1755 => 10,
  2347 => 10,
  1935 => 10,
  1059 => 10,
  233 => 10,
  954 => 10,
  590 => 10,
  596 => 10,
  229 => 300,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)