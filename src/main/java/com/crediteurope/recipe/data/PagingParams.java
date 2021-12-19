package com.crediteurope.recipe.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PagingParams {

	private Integer offset;
	private Integer limit;
	
}
