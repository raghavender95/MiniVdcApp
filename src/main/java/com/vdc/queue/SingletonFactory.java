package com.vdc.queue;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SingletonFactory {
	private static ConnectionFactory connectionFactory = null;
    private static Context context = null;
    private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    private static final String PROVIDER_URL = "http-remoting://localhost:8181";



    private SingletonFactory(){

    }

    public static ConnectionFactory getConnectionFactory() throws NamingException{

        if(connectionFactory == null){
            connectionFactory = (ConnectionFactory)getContext().lookup(DEFAULT_CONNECTION_FACTORY);
        }
        return connectionFactory;
    }


    public static Context getContext() throws NamingException{
        if(context == null){
            Properties initialProperties = new Properties();
            initialProperties.setProperty(InitialContext.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
            initialProperties.setProperty(InitialContext.PROVIDER_URL, PROVIDER_URL);    
            initialProperties.setProperty(InitialContext.SECURITY_PRINCIPAL, "guest");
            initialProperties.setProperty(InitialContext.SECURITY_CREDENTIALS, "GuesT123");

            context = new InitialContext(initialProperties);
        }
        return context;
    }

}
