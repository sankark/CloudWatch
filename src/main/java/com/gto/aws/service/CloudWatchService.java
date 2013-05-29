package com.gto.aws.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.handlers.RequestHandler;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchAsyncClient;
import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult;
import com.amazonaws.services.cloudwatch.model.ListMetricsRequest;

public class CloudWatchService {
	@Autowired
	private AmazonCloudWatchAsyncClient cloudWatchClient;
	
	public AmazonCloudWatchAsyncClient getCloudWatchClient() {
		return cloudWatchClient;
	}

	public void setCloudWatchClient(AmazonCloudWatchAsyncClient cloudWatchClient) {
		this.cloudWatchClient = cloudWatchClient;
	}

	public List listMetrics() {
    	RequestHandler requestHandler = null;
		ListMetricsRequest listMetricsRequest=new ListMetricsRequest();
		Collection<Dimension> dimensionsList = new ArrayList<Dimension>();
		Dimension dimensionFilter = new Dimension();
		dimensionFilter.setName("InstanceId");
		dimensionFilter.setValue("i-c30fa5a4");
		dimensionsList.add(dimensionFilter);
		GetMetricStatisticsRequest getMetricStatisticsRequest = new GetMetricStatisticsRequest();
		getMetricStatisticsRequest.setMetricName("CPUUtilization");
		Date startTime=new Date(System.currentTimeMillis()-1000000 * 5);
		Date endTime=new Date(System.currentTimeMillis());
		getMetricStatisticsRequest.setStartTime(startTime);
		getMetricStatisticsRequest.setEndTime(endTime);
		getMetricStatisticsRequest.setNamespace("AWS/EC2");
		Collection<String> statistics = new ArrayList<String>();
		statistics.add("Average");
		getMetricStatisticsRequest.setStatistics(statistics );
		getMetricStatisticsRequest.setPeriod(360);
		getMetricStatisticsRequest.setDimensions(dimensionsList);
		GetMetricStatisticsResult result = cloudWatchClient.getMetricStatistics(getMetricStatisticsRequest );
		List<Datapoint> dataPoints = result.getDatapoints();
			for (Iterator iterator = dataPoints.iterator(); iterator.hasNext();) {
			Datapoint datapoint = (Datapoint) iterator.next();
			System.out.println(datapoint.getAverage());
			
		}
		
			return dataPoints;
		
		
	}
}
