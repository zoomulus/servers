package com.zoomulus.servers.http;

import com.google.inject.AbstractModule;
import com.zoomulus.servers.http.responder.DefaultHttpResponder;
import com.zoomulus.servers.http.responder.HttpResponder;

public class TestHttpServerModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(HttpResponder.class).to(DefaultHttpResponder.class);
    }
}
