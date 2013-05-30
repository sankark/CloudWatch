package com.gto.aws.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import com.basho.riak.client.RiakLink;
import com.basho.riak.client.convert.RiakIndex;
import com.basho.riak.client.convert.RiakKey;
import com.basho.riak.client.convert.RiakLinks;

public class Ec2CpuUtilizationInstance implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
@RiakKey private String jobId;
@RiakIndex(name = "instance") transient private String instanceId;
@RiakIndex(name = "date")  transient private Date date;
@RiakLinks transient private Collection<RiakLink> hourlyDatalinks;

public Ec2CpuUtilizationInstance()
{

}


}
