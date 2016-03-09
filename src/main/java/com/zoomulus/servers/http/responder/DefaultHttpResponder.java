package com.zoomulus.servers.http.responder;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;

public final class DefaultHttpResponder extends HttpResponder
{
    @Override
    protected boolean haveMatchingResource(HttpRequest request)
    {
        return true;
    }

    @Override
    protected boolean isSupportedMethod(HttpRequest request)
    {
        return true;
    }

    @Override
    protected FullHttpResponse generateResponse(HttpRequest request, HttpHeaders headers)
    {
        return createSuccessResponse("Hello from com.zoomulus.servers.HttpServer");
    }
}
