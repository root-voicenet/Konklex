## Handles ipns
##
## Use this by the following format:
## example: /ipn/onebip/?player=Steve
##
## => :onebip_ipn
## packet = method.packet
## player = packet.get_string "player"
## print "#{player}"
##
## will return Steve.
##

on :http, :onebip_ipn do |ctx, channel, method|
  packet = method.packet
  channel.close
end

on :http, :paypal_ipn do |ctx, channel, method|
  packet = method.packet
  channel.close
end