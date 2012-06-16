# The general store.

# Define our values.
id = 4
name = "Fishing Supplies"
items = {
  # item id => item amount,
  305 => 10,
  307 => 10,
  309 => 10,
  301 => 10,
  313 => 1000,
  314 => 1000,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)