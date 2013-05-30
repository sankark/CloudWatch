package com.gto.aws.service;

import com.gto.aws.model.Data;
import com.gto.aws.model.Ec2CpuUtilizationInstance;
import com.gto.aws.model.Ec2CpuUtilizationJob;
import com.gto.aws.model.HourlyData;
import com.gto.aws.model.JobConstants;
import com.gto.aws.model.JobRequestInstance;
import com.gto.aws.model.User;


public class DAOFactory {
 
	
	public interface IUserDAO extends IGenericDAO<User> {}
	public interface IJobDetailsDAO extends IGenericDAO<Ec2CpuUtilizationJob> {}
	public interface IJobRequestDAO extends IGenericDAO<JobRequestInstance> {

		JobRequestInstance updateStatus(String id,String status);}
	public interface IDataDAO extends IGenericDAO<Data> {}
	public interface IHourlyDataDAO extends IGenericDAO<HourlyData> {}
	public interface IEc2CpuUtilizationInstanceDAO extends IGenericDAO<Ec2CpuUtilizationInstance> {}
	public interface IEc2CpuUtilizationJobDAO extends IGenericDAO<Ec2CpuUtilizationJob> {}
		
	
	// The use those interfaces as we declare entity-specific DAOs

	public static class UserDAO extends GenericDAO<User> implements IUserDAO {
		public UserDAO(){
			super(JobConstants.TEST_USER);
		}
	}
	
	public static class JobRequestDAO extends JobRequestGenericDAO implements IJobRequestDAO {
		public JobRequestDAO(){
			super(JobConstants.JOB_REQUESTS);
		}
		
	}
	
	
	public static class DataDAO extends GenericDAO<Data> implements IDataDAO {
		public DataDAO(){
			super(JobConstants.DATA_POINTS);
		}
	}
	
	
	public static class HourlyDataDAO extends GenericDAO<HourlyData> implements IHourlyDataDAO {
		public HourlyDataDAO(){
			super(JobConstants.HOURLY_DATA);
		}
	}
	
	
	public static class Ec2CpuUtilizationInstanceDAO extends GenericDAO<Ec2CpuUtilizationInstance> implements IEc2CpuUtilizationInstanceDAO {
		public Ec2CpuUtilizationInstanceDAO(){
			super(JobConstants.JOB_INSTANCES);
		}
	}
	
	public static class Ec2CpuUtilizationJobDAO extends GenericDAO<Ec2CpuUtilizationJob> implements IEc2CpuUtilizationJobDAO {
		public Ec2CpuUtilizationJobDAO(){
			super(JobConstants.JOB_DEFINITION);
		}
	}
	
	
 
	
	// Static-only usage pattern
	protected DAOFactory() {}
	
	public static IUserDAO getUserDao() {
		return new UserDAO();
	}
	
	public static IJobDetailsDAO getJobDetailsDao() {
		return new JobConstants.JobDetailsDAO();
	}
	
	public static IJobRequestDAO getJobRequestDao() {
		return new JobRequestDAO();
	}
	
	
	public static IDataDAO getDataDao() {
		return new DataDAO();
	}
	
	
	public static IHourlyDataDAO getHourlyDataDao() {
		return new HourlyDataDAO();
	}
	public static IEc2CpuUtilizationInstanceDAO getEc2CpuUtilizationInstanceDao() {
		return new Ec2CpuUtilizationInstanceDAO();
	}
	
	
	

}