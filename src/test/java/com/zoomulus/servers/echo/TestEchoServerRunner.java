package com.zoomulus.servers.echo;

import com.zoomulus.servers.Server;
import com.zoomulus.servers.TestServerRunnerBase;

public class TestEchoServerRunner extends TestServerRunnerBase
{
    @Override
    protected Server getServer()
    {
        return new EchoServer();
    }
    
    public static void main(final String[] args)
    {
        new TestEchoServerRunner().run(args);
    }
}
