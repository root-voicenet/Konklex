# The Grum's_Gold_Exchange

# Define our values
id = 32
name = "Grum's Gold Exchange"
items = {
  # item id => item amount,
  1635 => 0,
  1637 => 0,
  1639 => 0,
  1641 => 0,
  1643 => 0,
  1654 => 0,
  1656 => 0,
  1658 => 0,
  1660 => 0,
  1662 => 0,
  1692 => 0,
  1694 => 0,
  1696 => 0,
  1698 => 0,
  1700 => 0,
  11069 => 0,
  11072 => 0,
  11076 => 0,
  11085 => 0,
  11092 => 0,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)