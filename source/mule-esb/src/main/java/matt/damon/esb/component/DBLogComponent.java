package matt.damon.esb.component;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

public class DBLogComponent implements Callable {

	private static final Log LOGGER = LogFactory.getLog(RoutersComponent.class);

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		LOGGER.info("<---------------DBLogComponent-------------------->");
		return null;
	}

}
