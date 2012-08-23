# The The_Milk_Shop

# Define our values
id = 134
name = "The Milk Shop"
items = {
  # item id => item amount,
  1927 => 200,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)