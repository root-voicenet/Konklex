# The Blackjack_Seller

# Define our values
id = 124
name = "Blackjack Seller"
items = {
  # item id => item amount,
  6412 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)