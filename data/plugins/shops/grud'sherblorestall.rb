# The Grud's_Herblore_Stall

# Define our values
id = 78
name = "Grud's Herblore Stall"
items = {
  # item id => item amount,
  229 => 10,
  233 => 10,
  221 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)