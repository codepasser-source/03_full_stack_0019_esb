package matt.damon.esb.component;

import matt.damon.esb.pojo.RouterInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

public class RoutersComponent implements Callable {

	private static final Log LOGGER = LogFactory.getLog(RoutersComponent.class);

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		LOGGER.debug("<---------------RoutersComponent-------------------->");
		RouterInfo router = new RouterInfo();
		router.setAddress("www.baidu.com");
		eventContext.getMessage().setProperty("esb-router", router,
				PropertyScope.SESSION);
		return eventContext.getMessage().getPayload();
	}

}
