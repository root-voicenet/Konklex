# The Harpoon_Joe's_House_of_Rum

# Define our values
id = 122
name = "Harpoon Joe's House of Rum"
items = {
  # item id => item amount,
  1917 => 10,
  7157 => 10,
  1993 => 10,
  2003 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)