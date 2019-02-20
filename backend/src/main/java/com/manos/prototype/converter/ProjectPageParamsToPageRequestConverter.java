package com.manos.prototype.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.manos.prototype.controller.params.OrderAndPageParams;
import com.manos.prototype.controller.params.ProjectOrderAndPageParams;
import com.pastelstudios.convert.Converter;
import com.pastelstudios.paging.OrderClause;
import com.pastelstudios.paging.OrderDirection;
import com.pastelstudios.paging.PageRequest;

@Component
public class ProjectPageParamsToPageRequestConverter implements Converter<ProjectOrderAndPageParams, PageRequest>{

	@Override
	public PageRequest convert(ProjectOrderAndPageParams from) {
		PageRequest to = new PageRequest();
		
		to.setPage(from.getPage());
		to.setPageSize(from.getPageSize());
		
		OrderDirection orderDirection = null;
		if (OrderAndPageParams.DIRECTION_ASC.equals(from.getDirection())) {
			orderDirection = OrderDirection.ASCENDING;
		} else if (OrderAndPageParams.DIRECTION_DESC.equals(from.getDirection())){
			orderDirection = OrderDirection.DESCENDING;
		}
		
		String dateCreatedField = null;
		String productsCountField = null;
		List<OrderClause> orderClauses = new ArrayList<>();

		if (from.getField() != null) {
			if (from.getField().equals(ProjectOrderAndPageParams.DATE_CREATED)) {
				dateCreatedField = "date";
				OrderClause clause1 = new OrderClause(dateCreatedField, orderDirection);
				orderClauses.add(clause1);
			}
			else if (from.getField().equals(ProjectOrderAndPageParams.PRODUCTS_COUNT)){
				productsCountField = "product";
				OrderClause clause3 = new OrderClause(productsCountField, orderDirection);
				orderClauses.add(clause3);
			}
		}
		
		if (!orderClauses.isEmpty()) {
			to.setOrderClauses(orderClauses);
		}
		return to;
	}

}
