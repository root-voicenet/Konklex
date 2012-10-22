# The Agmundi_Quality_Clothes

# Define our values
id = 160
name = "Agmundi Quality Clothes"
items = {
  # item id => item amount,
  5034 => 10,
  5032 => 10,
  5046 => 10,
  5044 => 10,
  5052 => 10,
  5050 => 10,
  5040 => 10,
  5038 => 10,
  5028 => 10,
  5026 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)