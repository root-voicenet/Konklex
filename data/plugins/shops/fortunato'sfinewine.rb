# The Fortunato's_Fine_Wine

# Define our values
id = 97
name = "Fortunato's Fine Wine"
items = {
  # item id => item amount,
  1993 => 10,
  1935 => 10,
  7919 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)