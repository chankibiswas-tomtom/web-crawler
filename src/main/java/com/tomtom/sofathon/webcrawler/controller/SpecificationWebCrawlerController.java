package com.tomtom.sofathon.webcrawler.controller;

import com.tomtom.sofathon.webcrawler.service.SpecificationWebCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spec")
public class SpecificationWebCrawlerController {

    @Autowired
    private SpecificationWebCrawlerService specificationWebCrawlerService;

    @GetMapping(value = "/getUrl/{heading}")
    public String getURL(@PathVariable(name = "heading") final String heading) {
        return specificationWebCrawlerService.getURL(heading);
    }

    @PostMapping(value = "/buildURLCache")
    public void buildURLCache() {
        specificationWebCrawlerService.buildURLCache();
    }
}
