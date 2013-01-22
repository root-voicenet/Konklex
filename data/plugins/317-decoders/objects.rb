require 'java'
java_import 'org.apollo.game.event.impl.FirstObjectActionEvent'
java_import 'org.apollo.game.event.impl.SecondObjectActionEvent'
java_import 'org.apollo.game.event.impl.ThirdObjectActionEvent'

on :decode, 317, 132 do |packet|
  reader = GamePacketReader.new packet
  x = reader.unsigned DataType::SHORT, DataOrder::LITTLE, DataTransformation::ADD
  id = reader.unsigned DataType::SHORT
  y = reader.unsigned DataType::SHORT, DataTransformation::ADD
  FirstObjectActionEvent.new id, Position.new(x, y)
end

on :decode, 317, 252 do |packet|
  reader = GamePacketReader.new packet
  id = reader.unsigned DataType::SHORT, DataOrder::LITTLE, DataTransformation::ADD
  y = reader.unsigned DataType::SHORT, DataOrder::LITTLE
  x = reader.unsigned DataType::SHORT, DataTransformation::ADD
  SecondObjectActionEvent.new id, Position.new(x, y)
end

on :decode, 317, 70 do |packet|
  reader = GamePacketReader.new packet
  x = reader.unsigned DataType::SHORT, DataOrder::LITTLE
  y = reader.unsigned DataType::SHORT
  id = reader.unsigned DataType::SHORT, DataOrder::LITTLE, DataTransformation::ADD
  ThirdObjectActionEvent.new id, Position.new(x, y)
end