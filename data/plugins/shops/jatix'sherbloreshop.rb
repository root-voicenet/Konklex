# The Jatix's_Herblore_Shop

# Define our values
id = 55
name = "Jatix's Herblore Shop"
items = {
  # item id => item amount,
  229 => 10,
  233 => 10,
  221 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)