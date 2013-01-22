# The Silver_Cog_Silver_Stall

# Define our values
id = 150
name = "Silver Cog Silver Stall"
items = {
  # item id => item amount,
  2355 => 10,
  442 => 10,
  1714 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)