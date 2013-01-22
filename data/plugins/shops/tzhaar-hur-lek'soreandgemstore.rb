# The TzHaar-Hur-Lek's_Ore_and_Gem_Store

# Define our values
id = 112
name = "TzHaar-Hur-Lek's Ore and Gem Store"
items = {
  # item id => item amount,
  438 => 5,
  436 => 5,
  440 => 2,
  1623 => 1,
  1621 => 1,
  6571 => 1,
  9194 => 50,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)