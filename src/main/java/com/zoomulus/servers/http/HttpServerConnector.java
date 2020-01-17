package com.zoomulus.servers.http;

import com.google.inject.Injector;
import com.zoomulus.servers.ServerConnector;
import com.zoomulus.servers.http.responder.DefaultHttpResponder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.Optional;

public class HttpServerConnector implements ServerConnector
{
    private int port;
    private final Optional<Injector> injector;

    private HttpServerConnector(int port, Optional<Injector> injector) {
        this.port = port;
        this.injector = injector;
    }
    
    public static final String CODEC_HANDLER_NAME            = "codec_handler";
    public static final String COMPRESSOR_HANDLER_NAME       = "compressor_handler";
    public static final String AGGREGATOR_HANDLER_NAME       = "aggregator_handler";
    public static final String HTTP_REQUEST_HANDLER_NAME     = "http_request_handler";

    @Override
    public ChannelInitializer<?> getChannelInitializer()
    {
        return new ChannelInitializer<SocketChannel>()
        {
            @Override
            public void initChannel(final SocketChannel ch) throws Exception
            {
                ch.pipeline().addLast(CODEC_HANDLER_NAME,           new HttpServerCodec());
                ch.pipeline().addLast(AGGREGATOR_HANDLER_NAME,      new HttpObjectAggregator(512*1024));
                ch.pipeline().addLast(HTTP_REQUEST_HANDLER_NAME,
                        injector.isPresent()
                                ? injector.get().getInstance(HttpHandler.class)
                                : new HttpHandler(new DefaultHttpResponder()));
                
                if (compress())
                {
                    ch.pipeline().addAfter(CODEC_HANDLER_NAME, COMPRESSOR_HANDLER_NAME, new HttpContentCompressor());
                }
            }
        };
    }

    public int getPort() {
        return port;
    }
    
    protected boolean compress()
    {
        return false;
    }
    
    public static HttpServerConnectorBuilder builder()
    {
        return new HttpServerConnectorBuilder();
    }
    
    public static class HttpServerConnectorBuilder
    {
        int _port = 8080;
        Optional<Injector> _injector = Optional.empty();
        
        public HttpServerConnectorBuilder port(int port)
        {
            _port = port;
            return this;
        }
        
        public HttpServerConnectorBuilder injector(final Injector injector)
        {
            _injector = Optional.of(injector);
            return this;
        }
        
        public HttpServerConnector build()
        {
            return new HttpServerConnector(_port, _injector);
        }
    }
}
