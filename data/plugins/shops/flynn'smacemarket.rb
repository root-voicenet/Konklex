# The Flynn's_Mace_Market

# Define our values
id = 44
name = "Flynn's Mace Market"
items = {
  # item id => item amount,
  1422 => 10,
  1420 => 10,
  1424 => 10,
  1428 => 10,
  1430 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)