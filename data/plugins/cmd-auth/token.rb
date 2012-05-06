# This class holds all the payment and vote packages.

class TokenUtil

  # Redeems a vote package that the player has voted for.
  def self.redeemVotePackage(package, player)
    case package
      when 1
      when 2
      else
        return false
    end
    return true
  end

  # Redeems a payment package the player has payed for.
  def self.redeemPaymentPackage(package, player)
    case package
      when 1
      when 2
      else
        return false
    end
    return true
  end

end