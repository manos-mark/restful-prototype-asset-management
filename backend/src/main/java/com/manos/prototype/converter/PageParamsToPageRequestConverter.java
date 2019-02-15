package com.manos.prototype.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.manos.prototype.controller.params.OrderAndPageParams;
import com.pastelstudios.convert.Converter;
import com.pastelstudios.paging.OrderClause;
import com.pastelstudios.paging.PageRequest;

@Component
public class PageParamsToPageRequestConverter implements Converter<OrderAndPageParams, PageRequest>{

	@Override
	public PageRequest convert(OrderAndPageParams from) {
		PageRequest to = new PageRequest();
		
		to.setPage(from.getPage());
		to.setPageSize(from.getPageSize());
		
		List<OrderClause> orderClauses = new ArrayList<>();
//		orderClauses.add(from.getDirection());
//		orderClauses.add(from.getField());
//		to.setOrderClauses(orderClauses);
		return null;
	}

}
