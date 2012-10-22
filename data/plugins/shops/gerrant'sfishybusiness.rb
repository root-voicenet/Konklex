# The Gerrant's_Fishy_Business

# Define our values
id = 31
name = "Gerrant's Fishy Business"
items = {
  # item id => item amount,
  303 => 10,
  307 => 10,
  309 => 10,
  311 => 1000,
  301 => 10,
  313 => 1000,
  314 => 1000,
  317 => 0,
  327 => 10,
  345 => 0,
  321 => 0,
  335 => 0,
  349 => 0,
  331 => 0,
  359 => 0,
  377 => 0,
  371 => 0,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)