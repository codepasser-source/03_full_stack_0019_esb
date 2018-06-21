import com.taiping.esb.ServiceMetadata;

List services = message.getInboundProperty("tp-service-list");

def sw = new StringWriter()

def html = new groovy.xml.MarkupBuilder(sw)
html.html{
  head{
	meta(content:'text/html;charset=utf-8')
    title("̫ƽ����ƽ̨-����һ��")	
  }
  body{
    h2("̫ƽ����ƽ̨-����һ��")
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
