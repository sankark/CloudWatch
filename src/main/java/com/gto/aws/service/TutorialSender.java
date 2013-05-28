package com.gto.aws.service;

import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Create a template of the tutorial bean defined in the XML file and send 10 message
 * to the TUTORIAL-EXCHANGE configured in the rabbt-listener-contet.xml file with the routing key
 *"my.routingkey.1"
 *
 */
public class TutorialSender implements BeanFactoryAware {
	private static  BeanFactory beanFactory;
	
	public void sendMessage(String message){
		 AmqpTemplate aTemplate = (AmqpTemplate) beanFactory.getBean("tutorialTemplate");
		 for (int i = 0; i < 10; i++)
	            aTemplate.convertAndSend("my.routingkey.1", message+i);
		
	}
   

	@Override
	public void setBeanFactory(BeanFactory arg0) throws BeansException {
		// TODO Auto-generated method stub
		beanFactory = arg0;
	}
}
//end of TutorialSender