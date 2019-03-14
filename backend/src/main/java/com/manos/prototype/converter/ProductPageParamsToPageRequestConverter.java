package com.manos.prototype.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.manos.prototype.controller.params.OrderAndPageParams;
import com.manos.prototype.controller.params.ProductOrderAndPageParams;
import com.pastelstudios.convert.Converter;
import com.pastelstudios.paging.OrderClause;
import com.pastelstudios.paging.OrderDirection;
import com.pastelstudios.paging.PageRequest;

@Component
public class ProductPageParamsToPageRequestConverter implements Converter<ProductOrderAndPageParams, PageRequest>{

	@Override
	public PageRequest convert(ProductOrderAndPageParams from) { 
		PageRequest to = new PageRequest();
		
		to.setPage(from.getPage());
		to.setPageSize(from.getPageSize());
		
		OrderDirection orderDirection = null;
		if (OrderAndPageParams.DIRECTION_ASC.equals(from.getDirection())) {
			orderDirection = OrderDirection.ASCENDING;
		} else if (OrderAndPageParams.DIRECTION_DESC.equals(from.getDirection())){
			orderDirection = OrderDirection.DESCENDING;
		}
		
		String field = null;
		List<OrderClause> orderClauses = new ArrayList<>();

		if (from.getField() != null) {
			if (from.getField().equals(ProductOrderAndPageParams.DATE_CREATED)) {
				field = "product.createdAt";
				OrderClause clause1 = new OrderClause(field, orderDirection);
				orderClauses.add(clause1);
			}
			else if (from.getField().equals(ProductOrderAndPageParams.QUANTITY)){
				field = "product.quantity";
				OrderClause clause3 = new OrderClause(field, orderDirection);
				orderClauses.add(clause3);
			}
		}
		
		if (!orderClauses.isEmpty()) {
			to.setOrderClauses(orderClauses);
		}
		return to;
	}

}
