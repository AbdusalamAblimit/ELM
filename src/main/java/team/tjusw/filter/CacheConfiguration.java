package team.tjusw.filter;

import team.tjusw.util.CommonUtil;

public class CacheConfiguration {
	public static final boolean cache_log = true;
	
	public static void log_cached(String name)
	{
		System.out.println(CommonUtil.getCurrentDate()+": Gateway通过缓存返回了"+name);
	}
	public static void log_caching(String name)
	{
		System.out.println(CommonUtil.getCurrentDate()+": Gateway缓存了"+name);
	}
}
