# The Warriors'_Guild_Food_Shop

# Define our values
id = 50
name = "Warriors' Guild Food Shop"
items = {
  # item id => item amount,
  333 => 10,
  365 => 10,
  2289 => 10,
  6705 => 10,
  2003 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)