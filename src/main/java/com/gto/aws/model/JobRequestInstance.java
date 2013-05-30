package com.gto.aws.model;

import java.io.Serializable;
import java.util.Date;

import com.basho.riak.client.convert.RiakKey;

public class JobRequestInstance implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
@RiakKey String jobRequestId;
public String getJobRequestId() {
	return jobRequestId;
}
public void setJobRequestId(String jobRequestId) {
	this.jobRequestId = jobRequestId;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public Ec2CpuUtilizationJob getJob() {
	return job;
}
public void setJob(Ec2CpuUtilizationJob job) {
	this.job = job;
}

public JobRequestInstance()
{
	status = "pending";
}
String status;
Date date;
Ec2CpuUtilizationJob job;
}
