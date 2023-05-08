package team.tjusw.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BusinessOrderTypeCacheGetGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    private static final String PATH_PATTERN = "businesses/order-type/(\\d+)";

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            Pattern pattern = Pattern.compile(PATH_PATTERN);
            Matcher matcher = pattern.matcher(path);

            if (matcher.find()) {
                Integer orderTypeId = Integer.valueOf(matcher.group(1));

                if (BusinessCache.orderTypeMap.containsKey(orderTypeId)) {
                	if(CacheConfiguration.cache_log)
                		CacheConfiguration.log_cached("商家列表");
                	exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    return exchange.getResponse().writeWith(Mono.just(
                            exchange.getResponse().bufferFactory().wrap(
                                    BusinessCache.orderTypeMap.get(orderTypeId).getBytes()
                            )
                    )).doOnNext(__ -> exchange.getResponse().setStatusCode(HttpStatus.OK));
                }
            }
            return chain.filter(exchange);
        };
    }
}
