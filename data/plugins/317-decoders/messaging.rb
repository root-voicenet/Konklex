require 'java'
java_import 'org.apollo.game.event.impl.FriendsListEvent'
java_import 'org.apollo.game.event.impl.PrivateChatEvent'

on :decode, 317, 215, 118, 133, 74 do |packet|
  reader = GamePacketReader.new packet
  player = reader.signed DataType::LONG, DataTransformation::QUADRUPLE
  FriendsListEvent.new packet.opcode, player
end

on :decode, 317, 126 do |packet|
  reader = GamePacketReader.new packet
  player = reader.signed DataType::LONG, DataTransformation::QUADRUPLE
  length = packet.length - 8
  original = Java::byte[length].new
  reader.bytes original
  uncompressed = TextUtil.uncompress original, length
  uncompressed = TextUtil.filter_invalid_characters uncompressed
  uncompressed = TextUtil.capitalize uncompressed
  recompressed = length
  TextUtil.compress uncompressed, recompressed
  PrivateChatEvent.new player, recompressed
end