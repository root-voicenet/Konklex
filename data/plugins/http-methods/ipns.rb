## Handles ipns
##
## Use this by the following format:
## example: /ipn/onebip/?player=Steve
##
## => :onebip_ipn
## packet = method.packet
## reader = FrontendPacketReader.new packet
## player = reader.get_string "player"
## print "#{player}"
##
## will return Steve.
##
require 'java'
java_import 'org.apollo.backend.codec.FrontendPacketReader'

on :http, :onebip_ipn do |ctx, channel, method|
  print ":onebip_ipn => recieved\n"
  packet = method.packet
  reader = FrontendPacketReader.new packet
  iterator = packet.parameters.keys
  while(iterator.has_next)
    key = iterator.next
    value = reader.get_string key
    print "#{key} => #{value}\n"
  end
  channel.close
end

on :http, :paypal_ipn do |ctx, channel, method|
  print ":paypal_ipn => recieved\n"
  packet = method.packet
  reader = FrontendPacketReader.new packet
  iterator = packet.parameters.keys
  while(iterator.has_next)
    key = iterator.next
    value = reader.get_string key
    print "#{key} => #{value}\n"
  end
  channel.close
end