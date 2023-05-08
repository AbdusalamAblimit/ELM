package team.tjusw.elm.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BasicAuth {
	public static String[] decodeFromHeader(String header) throws Exception
	{
		byte[] base64Token = header.substring(6).getBytes("UTF-8");
	    byte[] decoded;
	    try {
	        decoded = Base64.getDecoder().decode(base64Token);
	    } catch (IllegalArgumentException e) {
	        throw new Exception("Failed to decode basic authentication token");
	    }

	    String token = new String(decoded, StandardCharsets.UTF_8);

	    int delim = token.indexOf(":");
	    if (delim == -1) {
	        throw new Exception("Invalid basic authentication token");
	    }
	    return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}
}
