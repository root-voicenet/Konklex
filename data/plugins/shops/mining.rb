# The mining store.

# Define our values.
id = 6
name = "Mining Supplies"
items = {
  # item id => item amount,
  4319 => 1,
  4359 => 1,
  4329 => 1,
  4351 => 1,
  4347 => 1,
  4343 => 1,
  4333 => 1,
  4341 => 1,
  4317 => 1,
  4339 => 1,
  4361 => 1,
  4327 => 1,
  4337 => 1,
  4355 => 1,
  4345 => 1,
  4357 => 1,
  4335 => 1,
  4325 => 1,
  4321 => 1,
  4353 => 1,
  4331 => 1
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)