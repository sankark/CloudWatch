package com.gto.aws.service;

import com.basho.riak.client.convert.Converter;
import com.gto.aws.model.Ec2CpuUtilizationInstance;

public class Ec2CpuUtilizationInstanceConvertor extends GenericCryoConvertor<Ec2CpuUtilizationInstance> implements Converter<Ec2CpuUtilizationInstance> {

	public Ec2CpuUtilizationInstanceConvertor(String bucket) {
		super(bucket);
		// TODO Auto-generated constructor stub
	}

}
