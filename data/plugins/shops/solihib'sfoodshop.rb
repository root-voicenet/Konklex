# The Solihib's_Food_Shop

# Define our values
id = 147
name = "Solihib's Food Shop"
items = {
  # item id => item amount,
  1963 => 10,
  4016 => 10,
  4014 => 100,
  4012 => 100,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)