# The Hunting_Supplies

# Define our values
id = 79
name = "Hunting Supplies"
items = {
  # item id => item amount,
  10010 => 5,
  10012 => 30,
  10150 => 5,
  10006 => 20,
  9950 => 1,
  9948 => 1,
  9949 => 1,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)