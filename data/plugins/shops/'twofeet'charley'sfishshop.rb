# The 'Two_Feet'_Charley's_Fish_Shop

# Define our values
id = 120
name = "'Two Feet' Charley's Fish Shop"
items = {
  # item id => item amount,
  321 => 10,
  363 => 0,
  341 => 10,
  345 => 10,
  377 => 0,
  353 => 10,
  327 => 10,
  317 => 10,
  371 => 0,
  359 => 0,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)