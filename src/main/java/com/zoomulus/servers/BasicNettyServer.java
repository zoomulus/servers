package com.zoomulus.servers;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.List;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicNettyServer implements Server
{
    private static Logger log = LoggerFactory.getLogger(BasicNettyServer.class);

    private final List<ServerConnector> connectors;
    private List<ChannelFuture> channels = Lists.newArrayList();
    private final EventLoopGroup masterGroup;
    private final EventLoopGroup slaveGroup;
    
    private boolean shutdownCalled = false;

    protected String getName()
    {
        return this.getClass().getSimpleName();
    }

    public BasicNettyServer(final ServerConnector connector)
    {
        this(Lists.newArrayList(connector));
    }

    public BasicNettyServer(final List<ServerConnector> connectors)
    {
        this.connectors = connectors;
        masterGroup = new NioEventLoopGroup();
        slaveGroup = new NioEventLoopGroup();
    }
    
    public void start()
    {
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() { shutdown(); }
        });
        
        log.info(String.format("Starting new server of type \"%s\"...", getName()));

        try
        {
            // for each connector, build a bootstrap, start and save the ChannelFuture
            for (final ServerConnector connector : connectors)
            {
                final ServerBootstrap bootstrap =
                        new ServerBootstrap()
                            .group(masterGroup, slaveGroup)
                            .channel(NioServerSocketChannel.class)
                            .childHandler(connector.getChannelInitializer())
                            .option(ChannelOption.SO_BACKLOG, 128)
                            .childOption(ChannelOption.SO_KEEPALIVE, true);
                channels.add(bootstrap.bind(connector.getPort()).sync());
            }
        }
        catch (final InterruptedException e) { }
        
        log.info("Startup complete.");
    }

    public void shutdown()
    {
        if (! shutdownCalled)
        {
            log.info(String.format("Shutting down server of type \"%s\"...", getName()));
        }
        
        slaveGroup.shutdownGracefully();
        masterGroup.shutdownGracefully();

        for (final ChannelFuture channel : channels)
        {
            try
            {
                channel.channel().closeFuture().sync();
            }
            catch (InterruptedException e) { }
        }
        
        if (! shutdownCalled)
        {
            shutdownCalled = true;
            log.info("Shutdown complete.");
        }
    }
}
