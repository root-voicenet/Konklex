# The Etceteria_Fish

# Define our values
id = 130
name = "Etceteria Fish"
items = {
  # item id => item amount,
  305 => 10,
  314 => 1000,
  313 => 1000,
  307 => 10,
  309 => 10,
  311 => 30,
  301 => 10,
  321 => 0,
  363 => 0,
  341 => 0,
  345 => 0,
  377 => 0,
  353 => 0,
  349 => 0,
  331 => 0,
  327 => 0,
  383 => 0,
  317 => 0,
  371 => 0,
  335 => 0,
  359 => 0,
  303 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)