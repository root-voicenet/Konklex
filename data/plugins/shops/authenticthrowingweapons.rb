# The Authentic_Throwing_Weapons

# Define our values
id = 11
name = "Authentic Throwing Weapons"
items = {
  # item id => item amount,
  825 => 1000,
  826 => 1000,
  827 => 300,
  828 => 300,
  829 => 100,
  830 => 100,
  800 => 1000,
  801 => 1000,
  802 => 300,
  803 => 300,
  804 => 100,
  805 => 100,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)