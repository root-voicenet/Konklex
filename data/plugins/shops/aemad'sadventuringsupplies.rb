# The Aemad's_Adventuring_Supplies

# Define our values
id = 29
name = "Aemad's Adventuring Supplies"
items = {
  # item id => item amount,
  227 => 10,
  1265 => 10,
  1349 => 10,
  2142 => 10,
  590 => 10,
  1759 => 100,
  882 => 1000,
  954 => 10,
  970 => 10,
  946 => 10,
  1935 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)