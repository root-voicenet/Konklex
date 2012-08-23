# The Ranael's_Super_Skirt_Store

# Define our values
id = 38
name = "Ranael's Super Skirt Store"
items = {
  # item id => item amount,
  1087 => 10,
  1081 => 10,
  1083 => 10,
  1085 => 10,
  1089 => 10,
  1091 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)