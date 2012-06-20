# The herblore store.

# Define our values.
id = 5
name = "Herblore store"
items = { 
  # item id => item amount,
  227 => 100, 233 => 100,
  237 => 100, 243 => 100, 1973 => 100, 249 => 100, 227 => 100,
  261 => 100, 263 => 100, 265 => 100, 267 => 100, 269 => 100,
  91 => 100, 221 => 100, 93 => 100, 235 => 100, 95 => 100,
  225 => 100
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)