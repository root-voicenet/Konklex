# The wc fm store.

# Define our values.
id = 7
name = "WC & FM Supplies"
items = {
  # item id => item amount,
  1511 => 10000,
  1521 => 10000,
  1519 => 10000,
  1517 => 10000,
  1515 => 10000,
  1513 => 10000,
  5605 => 100,
  590 => 100,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)
