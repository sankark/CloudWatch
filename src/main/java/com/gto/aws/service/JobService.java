package com.gto.aws.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.basho.riak.client.IRiakObject;
import com.basho.riak.client.RiakException;
import com.basho.riak.client.RiakRetryFailedException;
import com.basho.riak.client.bucket.Bucket;
import com.basho.riak.client.bucket.FetchBucket;
import com.basho.riak.client.cap.UnresolvedConflictException;
import com.basho.riak.client.convert.ConversionException;
import com.basho.riak.client.query.MapReduceResult;
import com.basho.riak.client.query.functions.NamedErlangFunction;
import com.basho.riak.client.query.indexes.BinIndex;
import com.basho.riak.client.raw.query.indexes.BinValueQuery;
import com.basho.riak.client.raw.query.indexes.IndexQuery;
import com.gto.aws.model.Data;
import com.gto.aws.model.Ec2CpuUtilizationJob;
import com.gto.aws.model.JobConstants;
import com.gto.aws.model.JobRequestInstance;


public class JobService {
	
	@Autowired
	JobRequestSender jobRequestSender;
	
	public void createJob(Ec2CpuUtilizationJob job) {
		
		DAOFactory.getJobDetailsDao().put(job);
		// TODO Auto-generated method stub

	}
	
	public void executeJob(JobRequestInstance job) {
		
		DAOFactory.getJobRequestDao().put(job);
		JobRequest jobRequest= new JobRequest();
		jobRequest.setJobRequestId(job.getJobRequestId());
		jobRequest.setResponse("PENDING");
		jobRequestSender.sendJobRequest(jobRequest);
		// TODO Auto-generated method stub

	}
	
	public void putDayJobRequests(){
		
		 
		
		Bucket bucket=null;
		try {
			bucket = RiakFactory.getRiakClient().fetchBucket( JobConstants.JOB_DEFINITION).execute();
		} catch (RiakRetryFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			    
			try {
				List<String> engineers = bucket.fetchIndex(BinIndex.named("job-type")).withValue(JobConstants.DAILY).execute();
				for (Iterator iterator = engineers.iterator(); iterator
						.hasNext();) {
					String string = (String) iterator.next();
					Ec2CpuUtilizationJob job = new Ec2CpuUtilizationJob();
					job.setJobId(string);
					job = bucket.fetch(job).withConverter(ConvertorFactory.getJobConvertor()).execute();
					System.out.println(job.getGroupName());
					
				}
			} catch (RiakException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public static void main(String[] args) {
		JobService serv = new JobService();
		/*Ec2CpuUtilizationJob job = new Ec2CpuUtilizationJob();
		job.setJobId("test1");
		List<String> instances= new ArrayList<String>();
		instances.add("i-c30fa5a4");
		job.setInstances(instances);
		serv.createJob(job );*/
		
		/*JobRequestInstance jobRequestInstance = new JobRequestInstance();
		jobRequestInstance.setJobRequestId(UUID.randomUUID().toString());
		jobRequestInstance.setDate(new Date());
		jobRequestInstance.setJob(DAOFactory.getJobDetailsDao().get("test1"));
		serv.executeJob(jobRequestInstance);*/
		
		//System.out.println(DAOFactory.getJobRequestDao().get("c177a400-81ab-4b00-80b6-3d0a76fe3186").getDate().toString());
		
		Ec2CpuUtilizationJob job = new Ec2CpuUtilizationJob();
		job.setGroupName("test2");
		job.setJobId("test2");
		job.setJobName("test2");
		ArrayList<String> al = new ArrayList<String>();
		al.add("test");
		job.setInstances(al);
		Bucket bucket=null;
		try {
			bucket = RiakFactory.getRiakClient().fetchBucket( JobConstants.JOB_DEFINITION).execute();
		} catch (RiakRetryFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bucket.store(job).withConverter(ConvertorFactory.getJobConvertor()).execute();
		} catch (RiakRetryFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnresolvedConflictException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConversionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		serv.putDayJobRequests();
		
		
	}
}
