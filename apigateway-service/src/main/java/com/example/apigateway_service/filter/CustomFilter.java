package com.example.apigateway_service.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest; //reactive - 비동기
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
//Spring Cloud Gateway 는 WebFlux 기반, 비동기 논블로킹으로 동작함. -> 그래서  동기 Blocking 을 쓰면 성능저하
//Gateway - Request - Response 가로 채서 무언가를 처리할 수 있는 역할이기에, 리엑티브 프로그래밍 방식으로 응답 이후의 작업을 작성
//Custom Filter 구현 - Spring CLoud Gateway 의 커스텀 필터를 만들기 위한 기본 클래스로 Abstract 상속
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    public CustomFilter() { //설정 클래스 등록
        super(Config.class);
    }

    @Override //실제 필터 로직을 구현하는 매서드
    public GatewayFilter apply(Config config) {
        //custom pre filter

        return (exchange, chain) -> { //chain은 다음 필터로 넘기기위해 사용
            //요청 객체
            ServerHttpRequest request = exchange.getRequest();
            //응답 객체
            ServerHttpResponse response = exchange.getResponse();

            log.info("Custom PRE filter: request id -? {}",request.getId());

            //Custom Post Filter
            return chain.filter(exchange).then(Mono.fromRunnable(() -> { //비동기 방식의 서버를 지원할 때, 단일값 전달할때, 모노
                log.info("Custom POST filter: response code -> {}",response.getStatusCode());
            }));
        };
    }

    public static class Config {
        //Put the configuration properties

    }
}
