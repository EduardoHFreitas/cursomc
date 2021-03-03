package com.eduardo.cursomc.resources.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URLUtils {

	public static String decodeStringParam(String param) {
		try {
			return URLDecoder.decode(param, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static List<Integer> decodeIntegerList(String idsCategorias) {
		List<Integer> ids = new ArrayList<>();

		if (!idsCategorias.isBlank()) {
			 ids = Arrays.asList(idsCategorias.split(",")).stream().map(i -> Integer.parseInt(i)).collect(Collectors.toList());
		}
		
		return ids;
	}
	
}
