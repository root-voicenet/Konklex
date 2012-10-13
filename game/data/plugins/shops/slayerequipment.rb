# The Slayer_Equipment

# Define our values
id = 54
name = "Slayer Equipment"
items = {
  # item id => item amount,
  4155 => 300,
  4156 => 10,
  4158 => 10,
  4150 => 3000,
  4161 => 1000,
  4162 => 10,
  4164 => 10,
  4166 => 10,
  4168 => 10,
  4170 => 10,
  4551 => 10,
  6664 => 1000,
  6696 => 1000,
  6708 => 10,
  7051 => 10,
  7159 => 10,
  7421 => 10,
  7432 => 1000,
  8923 => 10,
  10952 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)