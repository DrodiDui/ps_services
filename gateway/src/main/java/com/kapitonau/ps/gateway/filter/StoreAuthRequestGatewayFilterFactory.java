package com.kapitonau.ps.gateway.filter;


import com.kapitonau.ps.gateway.component.ResponseBodyRewriter;
import com.kapitonau.ps.gateway.dto.AuthResponse;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class StoreAuthRequestGatewayFilterFactory extends AbstractGatewayFilterFactory<StoreAuthRequestGatewayFilterFactory.Config> {

    private final ResponseBodyRewriter responseBodyRewriter;
    private final ModifyResponseBodyGatewayFilterFactory modifyResponseBodyGatewayFilterFactory;

    public StoreAuthRequestGatewayFilterFactory(
            ResponseBodyRewriter responseBodyRewriter,
            ModifyResponseBodyGatewayFilterFactory modifyResponseBodyGatewayFilterFactory
    ) {
        super(Config.class);
        this.responseBodyRewriter = responseBodyRewriter;
        this.modifyResponseBodyGatewayFilterFactory = modifyResponseBodyGatewayFilterFactory;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return modifyResponseBodyGatewayFilterFactory.apply(
                new ModifyResponseBodyGatewayFilterFactory.Config()
                        .setRewriteFunction(byte[].class, AuthResponse.class, responseBodyRewriter)
        );
    }

    public static class Config {

    }
}
