# The Candle_Seller

# Define our values
id = 136
name = "Candle Seller"
items = {
  # item id => item amount,
  36 => 999999999,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)