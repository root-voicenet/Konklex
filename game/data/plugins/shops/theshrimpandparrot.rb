# The The_Shrimp_and_Parrot

# Define our values
id = 114
name = "The Shrimp and Parrot"
items = {
  # item id => item amount,
  347 => 10,
  339 => 10,
  361 => 10,
  379 => 10,
  373 => 10,
  3144 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)