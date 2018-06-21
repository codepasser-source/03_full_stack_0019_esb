package matt.damon.esb.pojo;

import java.io.Serializable;

public class RouterInfo implements Serializable {

	private static final long serialVersionUID = -1253805695471543273L;

	private String id;
	private String address;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
