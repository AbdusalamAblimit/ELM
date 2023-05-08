package team.tjusw.filter;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import team.tjusw.util.CommonUtil;

import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR;

@Component
public class ResponseCachingFilter implements GlobalFilter, Ordered {
    private static Joiner joiner = Joiner.on("");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    	
    	if(needsToCached(exchange.getRequest()))
    		return handleCache(exchange,chain);
    	else
    		return chain.filter(exchange);
       
    }

    
    
    private Mono<Void> handleCache(ServerWebExchange exchange, GatewayFilterChain chain) {
    	 // 获取响应对象
        ServerHttpResponse response = exchange.getResponse();
        DataBufferFactory bufferFactory = response.bufferFactory();
        ServerHttpResponseDecorator responseDecorator = new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (getStatusCode().equals(HttpStatus.OK) && body instanceof Flux) {
                    // 获取响应 ContentType
                    String responseContentType = exchange.getAttribute(ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
                    // 记录 JSON 格式数据的响应体
                    if (!StringUtils.isEmpty(responseContentType) && responseContentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
                        Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                        // 解决返回体分段传输
                        return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                            List<String> list = Lists.newArrayList();
                            dataBuffers.forEach(dataBuffer -> {
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);
                                list.add(new String(content, Charset.forName("UTF-8")));
                            });
                            String responseData = joiner.join(list);
                            caching(exchange.getRequest(), responseData);
                            //log.info("<------------------------ RESPONSE RESULT = [{}]", responseData.replaceAll("\n","").replaceAll("\t",""));
                            return bufferFactory.wrap(responseData.getBytes());
                        }));
                    }
                }
                return super.writeWith(body);
            }
        };
        return chain.filter(exchange.mutate().response(responseDecorator).build());
	}

	private boolean needsToCached(ServerHttpRequest request)
    {
		return true;
    	
    }
	private void caching(ServerHttpRequest request,String responseData)
	{
		String path = request.getURI().getPath();
		if(path.startsWith("/businesses/order-type/"))
			cachingBusinessOrderType(path,responseData);
		else if(path.startsWith("/businesses/"))
			cachingBusiness(path,responseData);
		else if(path.startsWith("/foods/business/"))
			cachingBusinessFoodList(path,responseData);
		else if(path.startsWith("/foods/"))
			cachingFood(path,responseData);
		else
			return;
	}
    
    
    private void cachingFood(String path, String responseData) {
    	String PATH_PATTERN = "foods/(\\d+)";
    	Pattern pattern = Pattern.compile(PATH_PATTERN);
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            Integer foodId = Integer.valueOf(matcher.group(1));
            FoodCache.foodMap.put(foodId, responseData);
            if(CacheConfiguration.cache_log)
            	CacheConfiguration.log_caching("食品");
         
        }
		
	}



	private void cachingBusinessFoodList(String path, String responseData) {
    	String PATH_PATTERN = "foods/business/(\\d+)";
    	Pattern pattern = Pattern.compile(PATH_PATTERN);
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            Integer businessId = Integer.valueOf(matcher.group(1));
            FoodCache.businessFoodMap.put(businessId, responseData);
            if(CacheConfiguration.cache_log)
            	CacheConfiguration.log_caching("食品列表");
        }
		
	}



	private void cachingBusiness(String path, String responseData) {
    	String PATH_PATTERN = "businesses/(\\d+)";
    	Pattern pattern = Pattern.compile(PATH_PATTERN);
        Matcher matcher = pattern.matcher(path);

        if (matcher.find()) {
            Integer businessId = Integer.valueOf(matcher.group(1));
            BusinessCache.businessMap.put(businessId, responseData);
            if(CacheConfiguration.cache_log)
            	CacheConfiguration.log_caching("商家");
        }
		
	}



	private void cachingBusinessOrderType(String path, String responseData) {
    	String PATH_PATTERN = "businesses/order-type/(\\d+)";
    	Pattern pattern = Pattern.compile(PATH_PATTERN);
        Matcher matcher = pattern.matcher(path);

        if (matcher.find()) {
            Integer orderTypeId = Integer.valueOf(matcher.group(1));
            BusinessCache.orderTypeMap.put(orderTypeId, responseData);
            if(CacheConfiguration.cache_log)
            	CacheConfiguration.log_caching("商家列表");
        }
		
	}

	


	@Override
    public int getOrder() {
        // 必须要小于 -1
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER-1;
    }
}