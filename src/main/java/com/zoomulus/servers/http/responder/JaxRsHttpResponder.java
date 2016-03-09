package com.zoomulus.servers.http.responder;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import com.google.common.collect.Sets;

@Slf4j
public class JaxRsHttpResponder extends HttpResponder
{
    private final Set<Class<?>> responders = Sets.newConcurrentHashSet();
    
    @Inject
    public JaxRsHttpResponder(List <Class<?>> responders)
    {
        for (final Class<?> responder : responders)
        {
            addResponderIfJaxRS(responder);
        }
    }
    
    private void addResponderIfJaxRS(final Class<?> responder)
    {
        log.debug("Checking whether class {} is a JaxRS resource", responder.getName());
//        if (null != responder.getAnnotation(Path.class))
//        {
//            log.debug("Found JaxRS resource {}", responder.getName());
//            responders.add(responder);
//        }
    }    

    @Override
    protected boolean haveMatchingResource(HttpRequest request)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected boolean isSupportedMethod(HttpRequest request)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected FullHttpResponse generateResponse(HttpRequest request,
            HttpHeaders headers)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
