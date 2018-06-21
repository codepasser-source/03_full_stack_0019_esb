package com.taiping.esb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

public class QueryServicesComponent extends DBComponent implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		List<ServiceMetadata> services = this.GetServiceMetadatas();
		eventContext.getMessage().setProperty("tp-service-list", services, PropertyScope.INBOUND);
		return eventContext.getMessage().getPayload();
	}
	
	private List<ServiceMetadata> GetServiceMetadatas() throws SQLException {
		List<ServiceMetadata> services = new ArrayList<ServiceMetadata>();
		String sql = "select * from t_svc_metadata";
		List<Map<String, Object>> rows = this.queryForList(sql);
		for(Map<String, Object> map : rows) {
			ServiceMetadata service = new ServiceMetadata();
			service.setServiceId((String)map.get("SERVICE_ID"));
			service.setDescription((String)map.get("DESCRIPTION"));
			service.setName((String)map.get("NAME"));
			service.setOuterPotocol((String)map.get("OUTER_PROTOCOL"));
			service.setServiceType((String)map.get("SERVICE_TYPE"));
			services.add(service);
		}
		return services;
	}
}