# The Daga's_Scimitar_Smithy

# Define our values
id = 144
name = "Daga's Scimitar Smithy"
items = {
  # item id => item amount,
  1321 => 10,
  4587 => 10,
  1323 => 10,
  1329 => 10,
  1325 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)