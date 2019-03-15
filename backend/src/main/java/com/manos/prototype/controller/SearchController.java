package com.manos.prototype.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manos.prototype.dto.SearchDto;
import com.manos.prototype.service.SearchService;

@RestController
@RequestMapping("/search")
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@GetMapping("/{text}")
	public SearchDto getActivities(@PathVariable("text") String text) {
		return searchService.search(text);
	}
}
