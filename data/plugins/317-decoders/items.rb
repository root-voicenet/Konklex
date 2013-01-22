require 'java'
java_import 'org.apollo.game.event.impl.FirstItemOptionEvent'
java_import 'org.apollo.game.event.impl.FirstItemActionEvent'
java_import 'org.apollo.game.event.impl.SecondItemOptionEvent'
java_import 'org.apollo.game.event.impl.SecondItemActionEvent'
java_import 'org.apollo.game.event.impl.ThirdItemOptionEvent'
java_import 'org.apollo.game.event.impl.ThirdItemActionEvent'
java_import 'org.apollo.game.event.impl.FourthItemOptionEvent'
java_import 'org.apollo.game.event.impl.FourthItemActionEvent'
java_import 'org.apollo.game.event.impl.FifthItemOptionEvent'
java_import 'org.apollo.game.event.impl.FifthItemActionEvent'
java_import 'org.apollo.game.event.impl.ItemOnItemEvent'
java_import 'org.apollo.game.event.impl.PickupItemEvent'
java_import 'org.apollo.game.event.impl.ItemUsedOnObjectEvent'
java_import 'org.apollo.game.event.impl.MagicOnItemEvent'
java_import 'org.apollo.game.event.impl.ItemOnPlayerEvent'
java_import 'org.apollo.game.event.impl.ItemOnFloorEvent'
java_import 'org.apollo.game.event.impl.SwitchItemEvent'
java_import 'org.apollo.game.model.Position'

on :decode, 317, 122 do |packet|
  reader = GamePacketReader.new packet
  interface = reader.signed DataType::SHORT, DataOrder::LITTLE, DataTransformation::ADD
  slot = reader.unsigned DataType::SHORT, DataTransformation::ADD
  id = reader.signed DataType::SHORT, DataOrder::LITTLE
  FirstItemOptionEvent.new interface, id, slot
end

on :decode, 317, 145 do |packet|
  reader = GamePacketReader.new packet
  interface = reader.unsigned DataType::SHORT, DataTransformation::ADD
  slot = reader.unsigned DataType::SHORT, DataTransformation::ADD
  id = reader.unsigned DataType::SHORT, DataTransformation::ADD
  FirstItemActionEvent.new interface, id, slot
end

on :decode, 317, 41 do |packet|
  reader = GamePacketReader.new packet
  id = reader.unsigned DataType::SHORT
  slot = reader.unsigned DataType::SHORT, DataTransformation::ADD
  interface = reader.unsigned DataType::SHORT, DataTransformation::ADD
  SecondItemOptionEvent.new interface, id, slot
end

on :decode, 317, 117 do |packet|
  reader = GamePacketReader.new packet
  interface = reader.unsigned DataType::SHORT, DataOrder::LITTLE, DataTransformation::ADD
  id = reader.unsigned DataType::SHORT, DataOrder::LITTLE, DataTransformation::ADD
  slot = reader.unsigned DataType::SHORT, DataOrder::LITTLE
  SecondItemActionEvent.new interface, id, slot
end

on :decode, 317, 16 do |packet|
  reader = GamePacketReader.new packet
  id = reader.unsigned DataType::SHORT, DataTransformation::ADD
  slot = reader.unsigned DataType::SHORT, DataOrder::LITTLE, DataTransformation::ADD
  interface = reader.signed DataType::SHORT, DataOrder::LITTLE, DataTransformation::ADD
  ThirdItemOptionEvent.new interface, id, slot
end

on :decode, 317, 43 do |packet|
  reader = GamePacketReader.new packet
  interface = reader.unsigned DataType::SHORT, DataOrder::LITTLE
  id = reader.unsigned DataType::SHORT, DataTransformation::ADD
  slot = reader.unsigned DataType::SHORT, DataTransformation::ADD
  ThirdItemActionEvent.new interface, id, slot
end

on :decode, 317, 75 do |packet|
  reader = GamePacketReader.new packet
  interface = reader.signed DataType::SHORT, DataOrder::LITTLE, DataTransformation::ADD
  slot = reader.unsigned DataType::SHORT, DataOrder::LITTLE
  id = reader.unsigned DataType::SHORT, DataTransformation::ADD
  FourthItemOptionEvent.new interface, id, slot
end

on :decode, 317, 129 do |packet|
  reader = GamePacketReader.new packet
  slot = reader.unsigned DataType::SHORT, DataTransformation::ADD
  interface = reader.unsigned DataType::SHORT
  id = reader.unsigned DataType::SHORT, DataTransformation::ADD
  FourthItemActionEvent.new interface, id, slot
end

on :decode, 317, 87 do |packet|
  reader = GamePacketReader.new packet
  id = reader.unsigned DataType::SHORT, DataTransformation::ADD
  interface = reader.unsigned DataType::SHORT
  slot = reader.unsigned DataType::SHORT, DataTransformation::ADD
  FifthItemOptionEvent.new interface, id, slot
end

on :decode, 317, 135 do |packet|
  reader = GamePacketReader.new packet
  slot = reader.unsigned DataType::SHORT, DataOrder::LITTLE
  interface = reader.unsigned DataType::SHORT, DataTransformation::ADD
  id = reader.unsigned DataType::SHORT, DataOrder::LITTLE
  FifthItemActionEvent.new interface, id, slot
end

on :decode, 317, 53 do |packet|
  reader = GamePacketReader.new packet
  targetslot = reader.unsigned DataType::SHORT
  usedslot = reader.unsigned DataType::SHORT, DataTransformation::ADD
  targetid = reader.signed DataType::SHORT, DataOrder::LITTLE, DataTransformation::ADD
  targetint = reader.unsigned DataType::SHORT
  usedid = reader.signed DataType::SHORT, DataOrder::LITTLE
  usedint = reader.unsigned DataType::SHORT
  ItemOnItemEvent.new targetslot, usedslot, targetid, targetint, usedid, usedint
end

on :decode, 317, 236 do |packet|
  reader = GamePacketReader.new packet
  y = reader.signed DataType::SHORT, DataOrder::LITTLE
  id = reader.signed DataType::SHORT
  x = reader.signed DataType::SHORT, DataOrder::LITTLE
  PickupItemEvent.new id, x, y
end

on :decode, 317, 192 do |packet|
  reader = GamePacketReader.new packet
  reader.signed DataType::SHORT, DataOrder::LITTLE, DataTransformation::ADD
  object = reader.unsigned DataType::SHORT, DataOrder::LITTLE
  y = reader.signed DataType::SHORT, DataOrder::LITTLE, DataTransformation::ADD
  slot = reader.signed DataType::SHORT, DataOrder::LITTLE, DataTransformation::ADD
  x = reader.unsigned DataType::SHORT, DataOrder::LITTLE, DataTransformation::ADD
  id = reader.unsigned DataType::SHORT
  ItemUsedOnObjectEvent.new object, slot-128, id, x, y
end

on :decode, 317, 237 do |packet|
  reader = GamePacketReader.new packet
  slot = reader.unsigned DataType::SHORT
  id = reader.unsigned DataType::SHORT, DataTransformation::ADD
  interface = reader.unsigned DataType::SHORT
  spell = reader.unsigned DataType::SHORT, DataTransformation::ADD
  MagicOnItemEvent.new interface, id, slot, spell
end

on :decode, 317, 14 do |packet|
  reader = GamePacketReader.new packet
  interface = reader.signed DataType::SHORT, DataTransformation::ADD
  index = reader.signed DataType::SHORT
  id = reader.signed DataType::SHORT
  slot = reader.signed DataType::SHORT, DataOrder::LITTLE
  ItemOnPlayerEvent.new interface, index, id, slot
end

on :decode, 317, 25 do |packet|
  reader = GamePacketReader.new packet
  interface = reader.signed DataType::SHORT, DataOrder::LITTLE
  id = reader.unsigned DataType::SHORT, DataTransformation::ADD
  floor = reader.signed DataType::SHORT
  y = reader.unsigned DataType::SHORT, DataTransformation::ADD
  slot = reader.unsigned DataType::SHORT, DataOrder::LITTLE, DataTransformation::ADD
  x = reader.signed DataType::SHORT
  ItemOnFloorEvent.new interface, id, floor, slot, Position.new(x, y)
end

on :decode, 317, 214 do |packet|
  reader = GamePacketReader.new packet
  interface = reader.unsigned DataType::SHORT, DataOrder::LITTLE, DataTransformation::ADD
  inserting = reader.unsigned DataType::BYTE, DataTransformation::NEGATE
  oldslot = reader.unsigned DataType::SHORT, DataOrder::LITTLE, DataTransformation::ADD
  newslot = reader.unsigned DataType::SHORT, DataOrder::LITTLE
  SwitchItemEvent.new interface, (inserting == 1 ? true : false), oldslot, newslot
end