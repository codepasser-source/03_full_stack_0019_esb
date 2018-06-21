import com.taiping.esb.ServiceMetadata;

List services = message.getInboundProperty("tp-service-list");

def sw = new StringWriter()

def html = new groovy.xml.MarkupBuilder(sw)
html.html{
  head{
	meta(content:'text/html;charset=utf-8')
    title("太平服务平台-服务一览")	
  }
  body{
    h2("太平服务平台-服务一览")
    table(border:1){
      tr{
        th("ServiceId")
        th("Name")
		th("Description")
      }
	  services.each {
		  service ->
	      tr{
	        td(service.serviceId)
	        td(service.name)
			td(service.description)
	      }
	  }
    }
  }
}

result = sw.toString();
