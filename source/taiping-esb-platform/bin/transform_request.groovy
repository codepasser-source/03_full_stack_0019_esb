import com.taiping.esb.RouterInfo;

def router = message.getSessionProperty("tp-esb-router")

// parse xml
def sw = new StringWriter()

if (payload == null || payload.toString() == "") {
	throw new Exception("非法报文！")
}

def root = new XmlParser().parseText(payload.toString())
def xp = new XmlNodePrinter(new PrintWriter(sw))
xp.preserveWhitespace = true

// need transform
if (router.needTransform == '0') {
    // get the business message
	xp.print(root.DATA[0].find{1==1})
} else {
	xp.print(root)
}

// build the new xml
def builder = new groovy.xml.StreamingMarkupBuilder()
builder.encoding = router.encoding
def message = {
  mkp.xmlDeclaration()
  mkp.yieldUnescaped(sw.toString())
}
	
def sw1 = new StringWriter()
sw1 << builder.bind(message)

println("==============address:${router.address},contentType:${router.contentType}==============")

result = sw1.toString()
if (router.formatter != null && router.formatter != "") {
	println("================== ${router.formatter} ================");
	result = result.toString().replaceAll("\n", "").replaceAll("\r", "").replaceAll(">\\s+<", "><")
	result = String.format(router.formatter, URLEncoder.encode(result, router.encoding))
	println("==================" + result)
}