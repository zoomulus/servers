package com.zoomulus.servers.http.responder;

import static io.netty.buffer.Unpooled.copiedBuffer;

import java.util.Optional;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public abstract class HttpResponder
{
    public FullHttpResponse processRequest(final FullHttpRequest request)
    {
        FullHttpResponse response = createResponse(HttpResponseStatus.INTERNAL_SERVER_ERROR, "Missing case error");
        
        if (isSupportedVersion(request))
        {
            final HttpHeaders headers = request.headers();
            final Optional<String> host = Optional.ofNullable(headers.get("Host"));
            
            if (isAllowedHost(host))
            {
                if (haveMatchingResource(request))
                {
                    if (isSupportedMethod(request))
                    {
                        response = generateResponse(request, headers);
                    }
                    else
                    {
                        response = createResponse(HttpResponseStatus.METHOD_NOT_ALLOWED);
                    }
                }
                else
                {
                    response = createResponse(HttpResponseStatus.NOT_FOUND);
                }
            }
            else
            {
                response = createResponse(HttpResponseStatus.FORBIDDEN);
            }
        }
        else
        {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0,
                    HttpResponseStatus.HTTP_VERSION_NOT_SUPPORTED,
                    copiedBuffer("HTTP 1.1 Required".getBytes()));
        }

        if (HttpHeaders.isKeepAlive(request))
        {
            response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain");
        
        return response;
    }
    
    protected abstract boolean haveMatchingResource(final HttpRequest request);
    protected abstract boolean isSupportedMethod(final HttpRequest request);
    protected abstract FullHttpResponse generateResponse(final HttpRequest request, final HttpHeaders headers);
    
    protected boolean isSupportedVersion(final HttpRequest request)
    {
        return HttpVersion.HTTP_1_1 == request.getProtocolVersion();
    }
    
    protected boolean isAllowedHost(final Optional<String> host)
    {
        return true;
    }
    
    public static FullHttpResponse createSuccessResponse()
    {
        return createSuccessResponse("");
    }
    
    public static FullHttpResponse createSuccessResponse(final String payload)
    {
        return createResponse(HttpResponseStatus.OK, payload);
    }
    
    public static FullHttpResponse createResponse(final HttpResponseStatus status)
    {
        return createResponse(status, "");
    }
    
    public static FullHttpResponse createResponse(final HttpResponseStatus status, String payload)
    {
        return createResponse(status, payload.getBytes());
    }
    
    public static FullHttpResponse createResponse(final HttpResponseStatus status, byte[] payload)
    {
        return createResponse(status, payload, HttpVersion.HTTP_1_1);
    }
    
    public static FullHttpResponse createResponse(final HttpResponseStatus status, byte[] payload, final HttpVersion version)
    {
        if (0 < payload.length)
        {
            FullHttpResponse response = new DefaultFullHttpResponse(version, status, copiedBuffer(payload));
            response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, payload.length);
            return response;
        }
        else
        {
            return new DefaultFullHttpResponse(version, status);
        }
    }
}
