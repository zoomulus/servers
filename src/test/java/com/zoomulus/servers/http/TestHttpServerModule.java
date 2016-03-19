package com.zoomulus.servers.http;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.zoomulus.servers.ServerPort;
import com.zoomulus.servers.http.responder.DefaultHttpResponder;
import com.zoomulus.servers.http.responder.HttpResponder;

public class TestHttpServerModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(HttpResponder.class).to(DefaultHttpResponder.class);
        bind(Integer.class).annotatedWith(Names.named(HttpServer.LISTEN_PORT_NAME))
        .toInstance(ServerPort.ZoomulusPort(ServerPort.PortNumber.HTTP));
    }
}
