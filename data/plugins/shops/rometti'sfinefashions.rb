# The Rometti's_Fine_Fashions

# Define our values
id = 64
name = "Rometti's Fine Fashions"
items = {
  # item id => item amount,
  646 => 30,
  648 => 30,
  650 => 30,
  652 => 30,
  654 => 30,
  656 => 30,
  658 => 30,
  660 => 30,
  662 => 30,
  664 => 30,
  636 => 30,
  638 => 30,
  640 => 30,
  642 => 30,
  644 => 30,
  626 => 30,
  628 => 30,
  630 => 30,
  632 => 30,
}
type = SHOP_UBUYONLY

# Ship out the shop to the world.
shop = appendShop Shops.new(id, name, items, type)