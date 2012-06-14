# The general store.

# Define our values.
id = 1
name = "General Store"
items = { 
  # item id => item amount,
  2437 => 100, 2441 => 100, 2443 => 100, 2435 => 100, 2445 => 100, 3041 => 100, 15273 => 100, 386 => 100, 1079 => 100, 1093 => 100, 1113 => 100, 1128 => 100, 3749 => 100, 3753 => 100, 3755 => 100, 7461 => 100, 7462 => 100, 4151 => 100, 8850 => 100, 20072 => 100, 4587 => 100, 11732 => 100
}
type = SHOP_BUYANDSELL

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)