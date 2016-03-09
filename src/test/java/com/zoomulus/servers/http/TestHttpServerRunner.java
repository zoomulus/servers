package com.zoomulus.servers.http;

import javax.inject.Inject;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.zoomulus.servers.Server;
import com.zoomulus.servers.TestServerRunnerBase;

public class TestHttpServerRunner extends TestServerRunnerBase
{
    final Injector injector;
    
    @Inject
    public TestHttpServerRunner(final Injector injector)
    {
        this.injector = injector;
    }
    
    @Override
    protected Server getServer()
    {
        return injector.getInstance(HttpServer.class);
    }
    
    public static void main(final String[] args)
    {
        Guice.createInjector(new TestHttpServerModule()).getInstance(TestHttpServerRunner.class).run(args);
    }
}
