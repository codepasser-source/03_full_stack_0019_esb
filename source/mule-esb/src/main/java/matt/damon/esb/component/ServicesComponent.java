package matt.damon.esb.component;

import java.util.ArrayList;
import java.util.List;

import matt.damon.esb.pojo.ServiceInfo;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

public class ServicesComponent implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		System.out.println("<---------------DBLogComponent-------------------->");
		List<ServiceInfo> services = new ArrayList<ServiceInfo>();
		for (int i = 0; i < 3; i++) {
			ServiceInfo service = new ServiceInfo();
			service.setId(String.valueOf(i));
			service.setHost("localhost");
			service.setPort("9999");
			service.setContext("service-" + i);
			service.setName("service-" + i);
			service.setDescription("service-测试");
			services.add(service);
		}
		eventContext.getMessage().setProperty("esb-service-list", services,
				PropertyScope.INBOUND);
		return eventContext.getMessage().getPayload();
	}

}
