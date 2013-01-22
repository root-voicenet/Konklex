# The Garden_Centre

# Define our values
id = 48
name = "Garden Centre"
items = {
  # item id => item amount,
  8417 => 10,
  8419 => 10,
  8421 => 10,
  8423 => 10,
  8425 => 10,
  8427 => 10,
  8429 => 10,
  8431 => 10,
  8433 => 10,
  8435 => 10,
  8451 => 10,
  8453 => 10,
  8455 => 10,
  8457 => 10,
  8459 => 10,
  8461 => 10,
  8437 => 10,
  8439 => 10,
  8441 => 10,
  8443 => 10,
  8445 => 10,
  8447 => 10,
  8449 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)