# The Keppa_Kettilon's_Store

# Define our values
id = 91
name = "Keppa Kettilon's Store"
items = {
  # item id => item amount,
  361 => 0,
  329 => 10,
  339 => 10,
  379 => 0,
  373 => 0,
  385 => 0,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)