require 'java'
java_import 'org.apollo.game.event.impl.FirstNpcOptionEvent'
java_import 'org.apollo.game.event.impl.SecondNpcOptionEvent'
java_import 'org.apollo.game.event.impl.ThirdNpcOptionEvent'
java_import 'org.apollo.game.event.impl.FourthNpcOptionEvent'

on :decode, 317, 72 do |packet|
  reader = GamePacketReader.new packet
  slot = reader.signed DataType::SHORT, DataTransformation::ADD
  FirstNpcOptionEvent.new slot
end

on :decode, 317, 155 do |packet|
  reader = GamePacketReader.new packet
  slot = reader.unsigned DataType::SHORT, DataOrder::LITTLE
  SecondNpcOptionEvent.new slot
end

on :decode, 317, 17 do |packet|
  reader = GamePacketReader.new packet
  slot = reader.unsigned DataType::SHORT, DataOrder::LITTLE, DataTransformation::ADD
  ThirdNpcOptionEvent.new slot
end

on :decode, 317, 21 do |packet|
  reader = GamePacketReader.new packet
  slot = reader.unsigned DataType::SHORT
  FourthNpcOptionEvent.new slot
end