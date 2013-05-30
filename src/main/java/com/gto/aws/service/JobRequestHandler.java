package com.gto.aws.service;
 
import java.util.Iterator;
import java.util.List;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.cloudwatch.AmazonCloudWatchAsyncClient;
import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult;
import com.gto.aws.model.User;
/**
 * This class implements org.springframework.amqp.core.MessageListener.
 *  It is tied to TUTORIAL_EXCHANGE and listing to an anonomous queue
 *  which picks up message in the  TUTORIAL_EXCHANGE with a routing pattern of
 *  my.routingkey.1  specified in rabbt-listener-contet.xml file.
 */
public class JobRequestHandler {
	@Autowired
	private AmazonCloudWatchAsyncClient cloudWatchClient;
    public List<Datapoint> handleMessage() {
    	GetMetricStatisticsResult result = cloudWatchClient.getMetricStatistics(getMetricStatisticsRequest );
		List<Datapoint> dataPoints = result.getDatapoints();
			for (Iterator iterator = dataPoints.iterator(); iterator.hasNext();) {
			Datapoint datapoint = (Datapoint) iterator.next();
			System.out.println(datapoint.getAverage());
			
		}
    	
    	return dataPoints;
    }
}