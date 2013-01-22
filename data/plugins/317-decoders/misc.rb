require 'java'
java_import 'org.apollo.game.event.impl.CameraMovementEvent'
java_import 'org.apollo.game.event.impl.RegionLoadEvent'
java_import 'org.apollo.game.event.impl.ChatPrivacySettingsEvent'
java_import 'org.apollo.game.event.impl.ClientFocusChangeEvent'
java_import 'org.apollo.game.event.impl.KeepAliveEvent'

on :decode, 317, 86 do |packet|
  reader = GamePacketReader.new packet
  y = reader.signed DataType::SHORT
  x = reader.signed DataType::SHORT, DataTransformation::ADD
  CameraMovementEvent.new(x, y)
end

on :decode, 317, 121 do |packet|
  RegionLoadEvent.new
end

on :decode, 317, 95 do |packet|
  reader = GamePacketReader.new packet
  public = reader.unsigned DataType::BYTE
  private = reader.unsigned DataType::BYTE
  trade = reader.unsigned DataType::BYTE
  ChatPrivacySettingsEvent.new public, private, trade
end

on :decode, 317, 3 do |packet|
  reader = GamePacketReader.new packet
  focus = reader.signed DataType::BYTE
  ClientFocusChangeEvent.new (focus == 1 ? true : false)
end

on :decode, 317, 0 do |packet|
  KeepAliveEvent.new
end