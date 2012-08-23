# The Castle_Wars_Ticket_Exchange

# Define our values
id = 76
name = "Castle Wars Ticket Exchange"
items = {
  # item id => item amount,
  4071 => 999999999,
  4069 => 999999999,
  4070 => 999999999,
  4072 => 999999999,
  4068 => 999999999,
  4506 => 999999999,
  4504 => 999999999,
  4505 => 999999999,
  4507 => 999999999,
  4503 => 999999999,
  4511 => 999999999,
  4509 => 999999999,
  4510 => 999999999,
  4512 => 999999999,
  4508 => 999999999,
  4513 => 999999999,
  4514 => 999999999,
  4515 => 999999999,
  4516 => 999999999,
  4055 => 999999999,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)