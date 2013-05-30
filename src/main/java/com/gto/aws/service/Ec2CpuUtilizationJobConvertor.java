package com.gto.aws.service;

import com.basho.riak.client.convert.Converter;
import com.gto.aws.model.Ec2CpuUtilizationInstance;

public class Ec2CpuUtilizationJobConvertor<Ec2CpuUtilizationJob> extends GenericCryoConvertor<Ec2CpuUtilizationJob>  implements Converter<Ec2CpuUtilizationJob>  {

	public Ec2CpuUtilizationJobConvertor(String bucket) {
		super(bucket);
		// TODO Auto-generated constructor stub
	}

}
