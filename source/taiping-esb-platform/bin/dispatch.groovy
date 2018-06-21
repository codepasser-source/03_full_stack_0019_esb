def root = new XmlParser().parseText(payload.toString())

message.setInboundProperty("tp-esb-control", root.Control[0])
message