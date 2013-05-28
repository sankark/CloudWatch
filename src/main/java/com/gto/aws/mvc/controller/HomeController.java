/*
 * Copyright 2002-2010 the original author or authors
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */
package com.gto.aws.mvc.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import twitter4j.internal.http.RequestMethod;

import com.gto.aws.model.TwitterMessage;
import com.gto.aws.model.User;
import com.gto.aws.service.DAOFactory;
import com.gto.aws.service.TutorialSender;
import com.gto.aws.service.TwitterService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value="/SendMessage/{message}")
    public @ResponseBody String sendMessage(HttpServletRequest req,@PathVariable String message)
    {
    	TutorialSender sender = new TutorialSender();
    	sender.sendMessage(message);
    	return "success";
    }
    
    @RequestMapping(value="/AddUser/{userName}/{pwd}")
    public @ResponseBody String addUser(HttpServletRequest req,@PathVariable String userName,@PathVariable String pwd)
    {
    	User user = new User();
    	user.setLoginID(userName);
    	user.setPwd(pwd);
    	DAOFactory.getUserDao().put(user);
    	return "success";
    }
}

