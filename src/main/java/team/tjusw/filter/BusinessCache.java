package team.tjusw.filter;

import java.util.HashMap;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

public class BusinessCache {
	public static  HashMap<Integer, String> orderTypeMap = new HashMap<Integer,String>();
	public static  HashMap<Integer, String> businessMap = new HashMap<Integer,String>();
}
