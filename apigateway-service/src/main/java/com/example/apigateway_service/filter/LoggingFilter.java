package com.example.apigateway_service.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter() { //설정 클래스 등록
        super(Config.class);
    }

    @Override //실제 필터 로직을 구현하는 매서드
    public GatewayFilter apply(Config config) {
        //custom pre filter

        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) ->{
            //요청 객체
            ServerHttpRequest request = exchange.getRequest();
            //응답 객체
            ServerHttpResponse response = exchange.getResponse();

            //Filter Logging 작업

            log.info("Logging Filter baseMassage: {} ",config.getBaseMessage());
            //요청
            if (config.isPreLogger()) {
                log.info("Logging PRE Filter Start: request id -> {}",request.getId());
            }

            //Custom Post Filter
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                //응답
                if (config.isPostLogger()) {
                    log.info("Loggin POST Filter End: response id -> {}",response.getStatusCode());
                }
            }));
        }, Ordered.HIGHEST_PRECEDENCE); //우선순위설정 - Gateway 내의 우선순위를 정하는것이고 체인 순서대로면 Client -> Handler -> Global 이순으로 작동함 :
        return filter;
    }

    @Data
    public static class Config {
        //Put the configuration properties
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;

    }
}
