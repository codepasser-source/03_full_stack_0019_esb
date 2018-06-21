import com.taiping.esb.RouterInfo;
// initialize the route info
/*
class Router {
	def serviceId, appId, address, encoding, transform, contentType, formatter
} 
List routers = [
	new Router(serviceId:"SVC001", appId:"TP-WSSC", address:"localhost:8080/ESBTestService/queryVehicleBrands", encoding:"UTF-8", transform:true, contentType:"application/xml"),
	new Router(serviceId:"SVC002", appId:"TP-WSSC", address:"localhost:8080/ESBTestService/quoteCalculate", encoding:"UTF-8", transform:true, contentType:"application/xml"),
	new Router(serviceId:"SVC003", appId:"TP-WSSC", address:"localhost:8080/ESBTestService/quote", encoding:"UTF-8", transform:false, contentType:"application/xml"),
	new Router(serviceId:"SVC004", appId:"TP-WSSC", address:"localhost:8080/ESBTestService/formTest", encoding:"UTF-8", transform:true, contentType:"", formatter:"name=01&xml=%1s"),
];
*/

List routers = message.getInboundProperty("tp-routers");
List providers = message.getInboundProperty("tp-service-providers");
message.setInboundProperty("tp-routers", "");
message.setInboundProperty("tp-service-providers", "");

if (routers == null) {
	throw new Exception("ESB路由信息异常！");
}

if (payload == null) {
	throw new Exception("请求报文为空！")
}

routers.each { println(">>>>>>> router info: appId:${it.appId}, serviceId:${it.serviceId}, address:${it.address}, accessToken:${it.accessToken} ")}
providers.each { println(">>>>>>> provider info: serviceId:${it.serviceId}, host:${it.host}, port:${it.port}") }

println("############################# " + payload.toString())

def root = new XmlParser().parseText(payload.toString())
def control = root.CONTROL[0]
//def control = message.getInboundProperty("tp-esb-control")

println(">>>>>>> input info: ${control.SERVICE_ID.text()}, ${control.APP_ID.text()}")

def currentRouter = routers.find { 
    //r -> r.id == control.SERVICE_ID.text() && r.appId == control.APP_ID.text()
	r -> r.serviceId == control.SERVICE_ID.text() && r.appId == control.APP_ID.text()
}

// keep the route infos into session
message.setSessionProperty("tp-esb-control", control)

if (currentRouter != null) {
	
	// 如果后台设定当前app为admin，则不校验 ACCESS_TOKEN
	if (currentRouter.accessToken != '') {
		if (control.ACCESS_TOKEN == null || control.ACCESS_TOKEN.text() == '') {
			throw new Exception("授权码不能为空！")
		}
		if (control.ACCESS_TOKEN.text() != currentRouter.accessToken) {
			throw new Exception("该用户未授权！")
		}
	}
		
	// TODO: 权重策略
	def svcProvider = providers.findAll {
		p -> p.serviceId == currentRouter.serviceId
	}.max { p -> p.weight }
	if (svcProvider == null) {
		throw new Exception("服务提供者异常！");
	}

	if (currentRouter.address.startsWith("/")) {
		currentRouter.address = currentRouter.address.substring(1);
	}
	currentRouter.address = "${svcProvider.host}:${svcProvider.port}/${currentRouter.address}"
	currentRouter.requestId = control.REQUEST_ID.text()
	currentRouter.sendTime = control.REQUEST_TIME.text()
	currentRouter.providerId = svcProvider.providerId
	
	message.setSessionProperty("tp-esb-router", currentRouter)
	
	println("========= address: ${currentRouter.address}")
} else {
	if (!routers.any { r -> r.serviceId == control.SERVICE_ID.text() }) {
		throw new Exception("请求的服务不存在!")
	}
    throw new Exception("该用户没有访问权限!")
}

message