# The Harry's_Fishing_Shop

# Define our values
id = 10
name = "Harry's Fishing Shop"
items = {
  # item id => item amount,
  303 => 10,
  307 => 10,
  311 => 1000,
  301 => 10,
  313 => 1000,
  305 => 10,
  317 => 1000,
  327 => 0,
  345 => 0,
  353 => 0,
  341 => 0,
  321 => 0,
  359 => 0,
  377 => 0,
  363 => 0,
  371 => 0,
  383 => 0,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)