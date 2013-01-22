# The Fernahei's_Fishing_Hut

# Define our values
id = 117
name = "Fernahei's Fishing Hut"
items = {
  # item id => item amount,
  307 => 10,
  309 => 10,
  313 => 1000,
  314 => 1000,
  335 => 10,
  349 => 10,
  331 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)