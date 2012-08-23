# The Crossbow_Shop_-2-

# Define our values
id = 154
name = "Crossbow Shop -2-"
items = {
  # item id => item amount,
  9174 => 0,
  9177 => 0,
  9179 => 0,
  9181 => 0,
  9183 => 0,
  9185 => 0,
  9420 => 7,
  9423 => 10,
  9425 => 10,
  9427 => 10,
  9429 => 10,
  9431 => 0,
  9440 => 10,
  9442 => 10,
  9444 => 10,
  9446 => 10,
  9448 => 10,
  9450 => 10,
  9452 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)