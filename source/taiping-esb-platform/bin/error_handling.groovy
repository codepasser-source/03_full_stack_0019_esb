import com.taiping.esb.RouterInfo;

def control = message.getSessionProperty("tp-esb-control")
//def router = message.getSessionProperty("tp-esb-router")

def error_msg = exception.cause.message;
error_msg

def builder = new groovy.xml.StreamingMarkupBuilder()
builder.encoding = "UTF-8"

def getValue(node) {
	val = (node != null) ? node.text() : ""
}

def message = {
	mkp.xmlDeclaration()
	RESPONSE {
		CONTROL {
			REQUEST_ID( getValue( control.REQUEST_ID ) )
			SERVICE_ID( getValue( control.SERVICE_ID ) )
			APP_ID( getValue( control.APP_ID ) )
			RESPONSE_CODE("9999")
			ERROR_MESSAGE(error_msg)
			RESPONSE_TIME(new Date().format('yyyy-MM-dd HH:mm:ss.SSS'))
		}
	}
  }

def sw1 = new StringWriter()
sw1 << builder.bind(message)

sw1.toString()