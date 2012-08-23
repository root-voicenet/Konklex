# The Crossbow_Shop_-1-

# Define our values
id = 96
name = "Crossbow Shop -1-"
items = {
  # item id => item amount,
  9440 => 10,
  9442 => 10,
  9444 => 10,
  9446 => 10,
  9448 => 10,
  9450 => 10,
  9452 => 10,
  9420 => 10,
  9423 => 10,
  9425 => 10,
  9427 => 10,
  9429 => 0,
  9431 => 0,
  9174 => 0,
  9177 => 0,
  9179 => 0,
  9181 => 0,
  9183 => 0,
  9185 => 0,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)