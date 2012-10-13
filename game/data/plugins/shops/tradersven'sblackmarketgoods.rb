# The Trader_Sven's_Black_Market_Goods

# Define our values
id = 118
name = "Trader Sven's Black Market Goods"
items = {
  # item id => item amount,
  9644 => 10,
  9640 => 10,
  9642 => 10,
  9636 => 10,
  9638 => 10,
  9634 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)