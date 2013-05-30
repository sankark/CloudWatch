package com.gto.aws.service;

import com.gto.aws.model.JobRequestInstance;

public class JobRequestGenericDAO extends GenericDAO<JobRequestInstance>{

	public JobRequestGenericDAO(String Bucket) {
		super(Bucket);
		// TODO Auto-generated constructor stub
	}
	
	public JobRequestInstance updateStatus(String id,String status) {
		JobRequestInstance instance = get(id);
		instance.setStatus(status);
		return put(instance);
		
	}

}
