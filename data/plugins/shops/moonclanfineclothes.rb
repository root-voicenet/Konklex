# The Moon_Clan_Fine_Clothes

# Define our values
id = 82
name = "Moon Clan Fine Clothes"
items = {
  # item id => item amount,
  9068 => 10,
  9069 => 10,
  9070 => 10,
  9071 => 10,
  9072 => 10,
  9073 => 10,
  9074 => 10,
  1733 => 10,
  1734 => 1000,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)