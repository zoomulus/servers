package com.zoomulus.servers.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import com.zoomulus.servers.http.responder.HttpResponder;

@Slf4j
public class HttpHandler extends ChannelInboundHandlerAdapter
{
    private final HttpResponder responder;
    
    @Inject
    public HttpHandler(final HttpResponder responder)
    {
        this.responder = responder;
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception
    {
        if (msg instanceof FullHttpRequest)
        {
            ctx.writeAndFlush(responder.processRequest((FullHttpRequest) msg));
        }
        else
        {
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
    {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception
    {
        ctx.writeAndFlush(HttpResponder.createResponse(HttpResponseStatus.INTERNAL_SERVER_ERROR, cause.getMessage()));
        log.error("Channel exception caught", cause);
    }    
}
