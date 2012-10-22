# The Fremennik_Fishmonger

# Define our values
id = 83
name = "Fremennik Fishmonger"
items = {
  # item id => item amount,
  303 => 10,
  307 => 10,
  309 => 10,
  311 => 30,
  301 => 10,
  313 => 1000,
  314 => 1000,
  305 => 10,
  317 => 10,
  327 => 10,
  345 => 0,
  353 => 0,
  341 => 0,
  321 => 0,
  335 => 0,
  349 => 0,
  331 => 0,
  359 => 0,
  377 => 0,
  363 => 0,
  371 => 0,
  383 => 0,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)