package com.zoomulus.servers;

import java.util.Map;

import com.google.common.collect.Maps;

public abstract class TestServerRunnerBase
{
    protected static final String ARG_DURATION = "duration";
    
    protected abstract Server getServer();
    
    public void run(final String[] args)
    {
        int duration = Integer.valueOf(parseArgs(args).get(ARG_DURATION));
        
        Server server = getServer();
        
        if (duration == -1)
        {
            server.start();
        }
        else
        {
            Thread serverThread = new Thread(){
                @Override
                public void run() {
                    server.start();
                }
            };
            serverThread.start();
            
            try
            {
                Thread.sleep(duration*1000);
            }
            catch (InterruptedException e) { }
            
            server.shutdown();
        }
    }
    
    protected Map<String, String> parseArgs(final String[] args)
    {
        final Map<String, String> parsedArgs = Maps.newHashMap();
        
        int duration = -1;
        if (args.length > 0)
        {
            try
            {
                duration = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e) { }
        }
        
        parsedArgs.put(ARG_DURATION, String.valueOf(duration));
        
        return parsedArgs;
    }    
}
