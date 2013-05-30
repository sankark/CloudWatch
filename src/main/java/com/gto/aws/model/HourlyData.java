package com.gto.aws.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import com.basho.riak.client.RiakLink;
import com.basho.riak.client.convert.RiakKey;
import com.basho.riak.client.convert.RiakLinks;

public class HourlyData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@RiakKey String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	Date startTime;
	Date endTime;
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@RiakLinks transient private Collection<RiakLink> dataLinks;

}
