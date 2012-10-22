# The Crossbow_Shop_-3-

# Define our values
id = 138
name = "Crossbow Shop -3-"
items = {
  # item id => item amount,
  9174 => 10,
  9177 => 10,
  9179 => 10,
  9181 => 10,
  9183 => 10,
  9185 => 10,
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
  9429 => 10,
  9431 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)