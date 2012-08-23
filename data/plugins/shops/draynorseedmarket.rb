# The Draynor_Seed_Market

# Define our values
id = 99
name = "Draynor Seed Market"
items = {
  # item id => item amount,
  5318 => 10,
  5319 => 10,
  5324 => 10,
  5322 => 0,
  5320 => 0,
  5323 => 0,
  5321 => 0,
  5305 => 10,
  5306 => 10,
  5097 => 10,
  5096 => 10,
  5307 => 10,
  5308 => 10,
  5309 => 10,
  5310 => 10,
  5311 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)