package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showIndex() {
		return "index";
	}

	@RequestMapping(value = "/{page}", method = RequestMethod.GET)
	public String toPage(@PathVariable String page) {
		return page;
	}

	@RequestMapping(value = "/rest/page/{page}", method = RequestMethod.GET)
	public String toPage1(@PathVariable String page) {
		return page;
	}
}
