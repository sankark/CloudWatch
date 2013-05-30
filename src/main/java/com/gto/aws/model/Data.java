package com.gto.aws.model;

import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.basho.riak.client.convert.RiakKey;

public class Data extends Datapoint {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@RiakKey String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	 public void setUnit(String unit)
	    {
	       super.setUnit(unit);
	    }

}
