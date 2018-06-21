package com.taiping.esb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

public class GetRoutersComponent extends DBComponent implements Callable {
	
	private static String uid = null;
	private static Object lock = new Object();
	private static List<RouterInfo> routers = null;
	private static List<ServiceProvider> providers = null;
	private String runtimeMode;
	
	public void setRuntimeMode(String mode) {
		this.runtimeMode = mode;
	}
    	
	public Object onCall(MuleEventContext context) throws Exception
    {
		synchronized(lock) {
	    	if (routers == null) {	    		
	    		routers = getRoutersFromDB();
	    		providers = getProvidersFromDB();
	    		uid = java.util.UUID.randomUUID().toString();
	    	} else {
	    		// 开发模式，不缓存
	    		if ("dev".equals(this.runtimeMode)) {
	    			routers = getRoutersFromDB();
		    		providers = getProvidersFromDB();
		    		uid = java.util.UUID.randomUUID().toString();
	    		}
	    	}
		}
		
		MuleMessage msg = context.getMessage();
    	msg.setProperty("tp-routers", routers, PropertyScope.INBOUND);  
    	msg.setProperty("tp-service-providers", providers, PropertyScope.INBOUND);
    	System.out.println("<<<<<<<<<<<<<<<<  " + uid + "  >>>>>>>>>>>>>>>");    	
        
    	return msg.getPayload();
    }
	
	private List<ServiceProvider> getProvidersFromDB() throws SQLException {
		
    	List<ServiceProvider> providers = new ArrayList<ServiceProvider>();
    	String sql = "select * from SERVICE_PROVIDER_VIEW";
    	List<Map<String, Object>> rows = this.queryForList(sql);
    	
    	for(Map<String, Object> map : rows) {
			ServiceProvider provider = new ServiceProvider();
			provider.setHost((String)map.get("HOST"));
			provider.setPort(String.valueOf(map.get("PORT")));
			provider.setServiceId((String)map.get("SERVICE_ID"));
			provider.setProviderId((String)map.get("PROVIDER_ID"));
			Object objWeight = map.get("WEIGHT");
			if (objWeight != null) {
				provider.setWeight(Integer.parseInt(String.valueOf(objWeight)));
			}
			providers.add(provider);
		}
		return providers;
	}
	

	private List<RouterInfo> getRoutersFromDB() throws SQLException {
    	
    	List<RouterInfo> routers = new ArrayList<RouterInfo>();
    	String sql = "select * from ROUTER_VIEW";
    	List<Map<String, Object>> rows = this.queryForList(sql);
    	
    	for(Map<String, Object> map : rows) {
			RouterInfo router = new RouterInfo();
			router.setRouterId((String)map.get("ROUTER_ID"));
			router.setAddress((String)map.get("REAL_ADDRESS"));
			router.setAppId((String)map.get("APP_ID"));
			router.setServiceId((String)map.get("SERVICE_ID"));
			router.setContentType((String)map.get("CONTENT_TYPE"));
			router.setEncoding((String)map.get("ENCODING"));
			router.setFormatter((String)map.get("FORMATTER"));
			router.setNeedTransform((String)map.get("NEED_TRANSFORM"));
			router.setAccessToken((String)map.get("ACCESS_TOKEN"));
			routers.add(router);
		}
		return routers;
    }
}
