# The Davon's_Amulet_Store

# Define our values
id = 110
name = "Davon's Amulet Store"
items = {
  # item id => item amount,
  1718 => 0,
  1694 => 0,
  1696 => 0,
  1698 => 0,
  1700 => 0,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)