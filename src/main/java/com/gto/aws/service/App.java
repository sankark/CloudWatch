package com.gto.aws.service;
import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.RiakFactory;
import com.basho.riak.client.RiakException;

public class App
{
    public static void main(String[] args) throws RiakException
    { 
        // Riak HTTP client with defaults
    
        // Riak HTTP client using supplied URL
       
        // Riak Protocol Buffers client with supplied IP and Port
        IRiakClient myPbClient = RiakFactory.pbcClient("i-88b704f6", 8081);

      
        myPbClient.shutdown();
    }   
}