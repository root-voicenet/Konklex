# The Gianne's_Restaurant

# Define our values
id = 66
name = "Gianne's Restaurant"
items = {
  # item id => item amount,
  2227 => 10,
  2219 => 10,
  2221 => 10,
  2225 => 10,
  2223 => 10,
  2233 => 10,
  2187 => 10,
  2195 => 10,
  2185 => 10,
  2205 => 10,
  2217 => 10,
  2209 => 10,
  2241 => 10,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)