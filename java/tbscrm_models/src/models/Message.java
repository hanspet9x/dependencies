package models;

import java.io.Serializable;

public class Message<T> implements Serializable{
	
	
	public static final String GET_VARIABLES = "get all tbs variables or options from corp";
	
	public static final String NEW_CUSTOMER = "add new zonal customer to corp";

	public static final String UPDATE_CUSTOMER = "update customer to corp";

	public static final String GET_CUSTOMERS = "all zonal customers from corp";

	public static final String GET_ENUMERATIONS = "all zonal enumerations from corp";

	public static final String CONNECT = "corporation init configuration";
	
	public static final String PING = "ping local server";
	
	public static final String GET_EMPLOYEES = "get all corporation employees";

	public static final String GET_ZONES = "get zones";

	public static final String GET_BILLS = "get zones";

	public static final String GET_BOREHOLE_LICENSE = "borehole license";

	public static final String GET_COMPLAINT_CODES = "complaint codes";

	public static final String GET_CUSTOMER_CATEGORY = "customer category";

	public static final String ADD_COMPLAINTS = "add complaints";

	public static final String UPDATE_COMPLAINTS = "update complaints";

	public static final String GET_SERVICE_AREAS = "service areas";

	public static final String GET_STREETS = "streets";

	public static final String GET_CUSTOMER_TYPES = "customer types";

	public static final String GET_METERED_WATER_TARIFF = "metered tariffs";

	public static final String GET_SCHEMES = "customer schemes";

	public static final String GET_SEWERAGE_TARIFF = "sewerage tariffs";

	public static final String GET_SUB_ZONES = "customer cc";

	public static final String GET_UN_METERED_WATER_TARIFFS = "un metered tarrifss";
	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;
	private String from;
	private T message;
	private String type;
	private String to;
	
	public Message() {
		super();
	}



	public Message(String from, T message, String type) {
		super();
		this.from = from;
		this.message = message;
		this.type = type;
	}

	public Message(String from, String to, T message, String type) {
		this.from = from;
		this.message = message;
		this.type = type;
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public T getMessage() {
		return message;
	}

	public void setMessage(T message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}
