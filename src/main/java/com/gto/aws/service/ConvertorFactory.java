package com.gto.aws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.convert.Converter;
import com.gto.aws.model.Ec2CpuUtilizationJob;
import com.gto.aws.model.JobConstants;
import com.gto.aws.model.User;


public class ConvertorFactory {
 
	
	public interface IJobConvertor extends IGenericCryoConvertor<Ec2CpuUtilizationJob> {}
		
	
	// The use those interfaces as we declare entity-specific DAOs

	public static class JobConvertor extends GenericCryoConvertor<Ec2CpuUtilizationJob> implements IJobConvertor {
		public JobConvertor(){
			super(JobConstants.JOB_DEFINITION);
		}
	}
	
	
 
	// Static-only usage pattern
	protected ConvertorFactory() {}
	
	public static Converter<Ec2CpuUtilizationJob> getJobConvertor() {
		return new JobConvertor();
	}
	

}