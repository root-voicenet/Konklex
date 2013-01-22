# The Candle_Shop

# Define our values
id = 60
name = "Candle Shop"
items = {
  # item id => item amount,
  36 => 10,
  38 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)