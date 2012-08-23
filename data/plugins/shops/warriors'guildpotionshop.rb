# The Warriors'_Guild_Potion_Shop

# Define our values
id = 49
name = "Warriors' Guild Potion Shop"
items = {
  # item id => item amount,
  115 => 10,
  121 => 10,
  133 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)