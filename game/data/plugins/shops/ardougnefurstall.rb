# The Ardougne_Fur_Stall

# Define our values
id = 26
name = "Ardougne Fur Stall"
items = {
  # item id => item amount,
  948 => 10,
  958 => 10,
  10117 => 0,
  10121 => 0,
  10119 => 0,
  10123 => 0,
  10093 => 0,
  10095 => 0,
  10097 => 0,
  10099 => 0,
  10101 => 0,
  10103 => 0,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)