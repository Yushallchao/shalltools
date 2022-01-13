package com.xiao.ibmmqjms;

import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

public class JNDIUtil {
    private Context context;

    public JNDIUtil(String icf, String url) throws NamingException {
        Hashtable<String, String> environment = new Hashtable<String, String>();

        environment.put(Context.INITIAL_CONTEXT_FACTORY, icf );
        environment.put(Context.PROVIDER_URL, url);

        context= new InitialContext( environment );

    }

    /**
     *
     * @param ObjName Object Name to be retrieved
     * @return Retrieved Object
     * @throws NamingException
     */
    private Object getObjectByName(String ObjName) throws NamingException
    {
        return context.lookup( ObjName );
    }

    /**
     * A convenience method that returns QueueConnectionFactory objects (no casting required)
     * @param factoryName QueueConnectionFactory JNDI name
     * @return QueueConnectionFactory object
     * @throws NamingException
     */
    public QueueConnectionFactory getQueueConnectionFactory(String factoryName) throws NamingException
    {
        return  (QueueConnectionFactory) getObjectByName(factoryName);
    }

    /**
     * A convenience method that returns Queue objects (no casting required)
     * @param queueName
     * @return
     * @throws NamingException
     */
    public Queue getQueue(String queueName) throws NamingException
    {
        return (Queue) getObjectByName(queueName);
    }

}
