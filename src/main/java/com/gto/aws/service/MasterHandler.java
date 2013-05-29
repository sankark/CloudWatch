package com.gto.aws.service;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.cloudwatch.model.Datapoint;

public class MasterHandler {

	private static final Logger logger = LoggerFactory.getLogger(MasterHandler.class);

	

	public void handleMessage(List<Datapoint> resultMessage) {

		for (Iterator iterator = resultMessage.iterator(); iterator.hasNext();) {
			Datapoint datapoint = (Datapoint) iterator.next();
			System.out.println(datapoint.getAverage());
			
		}
		

	}

	

}