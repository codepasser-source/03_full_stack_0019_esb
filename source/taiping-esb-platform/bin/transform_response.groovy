import com.taiping.esb.RouterInfo;

def control = message.getSessionProperty("tp-esb-control")
def router = message.getSessionProperty("tp-esb-router")
def getValue(node) {
	val = (node != null) ? node.text() : ""
}

if (payload == null || payload == "") {
	
	def builder = new groovy.xml.StreamingMarkupBuilder()
	builder.encoding = "UTF-8"
	
	def message = {
	  mkp.xmlDeclaration()
	  RESPONSE {
		  CONTROL {
			  REQUEST_ID( getValue( control.REQUEST_ID ) )
			  SERVICE_ID( getValue( control.SERVICE_ID ) )
			  APP_ID( getValue( control.APP_ID ) )
			  RESPONSE_CODE("0")
			  RESPONSE_TIME(new Date().format('yyyy-MM-dd HH:mm:ss.SSS'))
		  }
		  DATA()
	  }
	}
	
	def swResult = new StringWriter()
	swResult << builder.bind(message)
	
	result = swResult.toString()
	
} else {
	def sw = new StringWriter()
	def root = new XmlParser().parseText(payload.toString())
	def xp = new XmlNodePrinter(new PrintWriter(sw))
	xp.preserveWhitespace = true
	
	// need transform
	if (router.needTransform == '0') {
		xp.print(root)
	} else {
		def data = root.DATA[0]
		xp.print(data)
	}
	
	def builder = new groovy.xml.StreamingMarkupBuilder()
	builder.encoding = "UTF-8"
	
	def message = {
	  mkp.xmlDeclaration()
	  RESPONSE {
		  CONTROL {
			  REQUEST_ID( getValue( control.REQUEST_ID ) )
			  SERVICE_ID( getValue( control.SERVICE_ID ) )
			  APP_ID( getValue( control.APP_ID ) )
			  RESPONSE_CODE("0")
			  RESPONSE_TIME(new Date().format('yyyy-MM-dd HH:mm:ss.SSS'))
		  }
		  DATA {
			  mkp.yieldUnescaped(sw.toString())
		  }
	  }
	}
	
	def swResult = new StringWriter()
	swResult << builder.bind(message)
	
	result = swResult.toString()
}