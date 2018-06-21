import matt.damon.esb.pojo.ServiceInfo;

List services = message.getInboundProperty("service-list");

def sw = new StringWriter()

def html = new groovy.xml.MarkupBuilder(sw)
html.html{
  head{
	meta(content:'text/html;charset=utf-8')
    title("service - list view")	
  }
  body{
    h2("service - list view")
    table(border:1){
      tr{
        th("ID")
        th("HOST")
		th("PORT")
		th("CONTEXT")
		th("NAME")
		th("DESCRIPTION")
      }
	  services.each {
		  service ->
	      tr{
	        td(service.id)
	        td(service.host)
			td(service.port)
			td(service.context)
			td(service.name)
			td(service.description)
	      }
	  }
    }
  }
}

result = sw.toString();
