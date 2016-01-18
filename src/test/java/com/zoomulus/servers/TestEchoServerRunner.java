package com.zoomulus.servers;

import com.zoomulus.servers.echo.EchoServer;

public class TestEchoServerRunner
{
    public static void main(final String[] args)
    {
        int duration = -1;
        if (args.length > 0)
        {
            try
            {
                duration = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e) { }
        }
        
        Server echoServer = new EchoServer();
        if (duration == -1)
        {
            echoServer.start();
        }
        else
        {
            Thread serverThread = new Thread(){
                @Override
                public void run() {
                    echoServer.start();
                }
            };
            serverThread.start();
            
            try
            {
                Thread.sleep(duration*1000);
            }
            catch (InterruptedException e) { }
            
            echoServer.shutdown();
        }
    }
}
