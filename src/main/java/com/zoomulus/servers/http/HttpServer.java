package com.zoomulus.servers.http;

import javax.inject.Inject;

import com.google.inject.Injector;
import com.zoomulus.servers.BasicNettyServer;
import com.zoomulus.servers.ServerPort;

public class HttpServer extends BasicNettyServer
{
    @Inject
    public HttpServer(final Injector injector)
    {
        super(HttpServerConnector.builder()
                .port(ServerPort.ZoomulusPort(ServerPort.PortNumber.HTTP))
                .injector(injector)
                .build());
    }
    
    public HttpServer()
    {
        super(HttpServerConnector.builder()
                .port(ServerPort.ZoomulusPort(ServerPort.PortNumber.HTTP))
                .build());
    }
}
