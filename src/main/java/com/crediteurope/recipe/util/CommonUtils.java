package com.crediteurope.recipe.util;

import com.crediteurope.recipe.data.PagingParams;

public class CommonUtils {

	private static final Integer DEFAULT_LIMIT = 100;

	public static PagingParams fixParameters(Integer offset, Integer limit) {
		if (offset == null || offset <= 0) {
			offset = 0;
		}
		if (limit == null || limit <= 0) {
			limit = DEFAULT_LIMIT;
		}
		return new PagingParams(offset, limit);
	}

}
