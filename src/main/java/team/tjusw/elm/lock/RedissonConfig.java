package team.tjusw.elm.lock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedissonConfig {
	public final String host;
	public final Integer port;
	public final Integer timeout;
	
	
	public RedissonClient redissonClient;
	
	public RedissonConfig(	@Value("${redisClientConfig.host}") String host,
			 			   	@Value("${redisClientConfig.port}") Integer port,
			 			    @Value("${redisClientConfig.timeout}") Integer  timeout)
	{ 
		this.host = host;
		this.port = port;
		this.timeout = timeout; 
	 	Config config = new Config(); 
        config.useSingleServer().setAddress("redis://"+host+":"+port);
        redissonClient =  Redisson.create(config);
	}
}
