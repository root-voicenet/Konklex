# The Aaron's_Archery_Appendages

# Define our values
id = 141
name = "Aaron's Archery Appendages"
items = {
  # item id => item amount,
  1169 => 10,
  1131 => 10,
  1129 => 10,
  1095 => 10,
  1167 => 10,
  1063 => 10,
  1133 => 10,
  1097 => 10,
  9758 => 1,
  9756 => 1,
  9757 => 1,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)