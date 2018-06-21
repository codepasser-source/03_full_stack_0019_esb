package com.taiping.esb;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;
import org.springframework.web.util.HtmlUtils;

public class DBLogComponent extends DBComponent implements Callable {
	
	private String operation = "";
	private String msgType = "";

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMsgType() {
		return msgType;
	}
	
	private static final String MSG_LOG_SQL = "insert into t_svc_msg_log (ID, MSG_TYPE, " +
			"REQUEST_ID, SERVICE_ID, APP_ID, ACCESS_TOKEN, " +
			"ROUTER_ID, PROVIDER_ID, ADDRESS, NEED_TRANSFORM, " +
			"CONTENT_TYPE, ENCODING, FORMATTER, PAYLOAD, " +
			"SEND_TIME) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private static final String ERR_LOG_SQL = "insert into t_svc_error_log (ID, " +
			"REQUEST_ID, SERVICE_ID, ROUTER_ID, PROVIDER_ID, " +
			"APP_ID, PAYLOAD, EXCEPTION, SET_TIME) values (?,?,?,?,?,?,?,?,?)";
	
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		MuleMessage msg = eventContext.getMessage();
		String id = java.util.UUID.randomUUID().toString().replaceAll("-", "");
		
		RouterInfo router = msg.getProperty("tp-esb-router", PropertyScope.SESSION);
		String payload = msg.getPayloadAsString();
		payload = HtmlUtils.htmlEscape(payload).replaceAll("\r|\n", "");
		if (payload.length() > 5000) {
			payload = payload.substring(0, 4999);
		}

		try {
			System.out.println("****************************" + this.operation + ", " + this.msgType);
			Object[] params = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
			String datestr = sdf.format(new Date());
			
			if ("msg".equals(this.operation)) {
				if (router == null) {
					params = new Object[] {
						id, msgType, null, null, null,
						null, null, null, null,
						null, null, null,
						null, payload, datestr
					};
				} else {
					params = new Object[] {
						id, msgType, router.getRequestId(), router.getServiceId(), router.getAppId(), 
						router.getAccessToken(), router.getRouterId(), router.getProviderId(), router.getAddress(),
						router.getNeedTransform(), router.getContentType(), router.getEncoding(),
						router.getFormatter(), payload, router.getSendTime()
					};
				}				
				this.insert(MSG_LOG_SQL, params);
				
			} else if ("err".equals(this.operation)) {
				
				if (router == null) {
					params = new String[] {
						id, null, null, null, null, null, payload,
						msg.getExceptionPayload().getException().getMessage(), datestr
					};
				} else {
					params = new String[] {
						id, router.getRequestId(), router.getServiceId(), router.getRouterId(),
						router.getProviderId(), router.getAppId(), payload,
						msg.getExceptionPayload().getException().getMessage(), datestr
					};
				}

				this.insert(ERR_LOG_SQL, params);
			}
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return msg.getPayloadAsString();
	}
}