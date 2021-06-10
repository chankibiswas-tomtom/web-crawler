package com.tomtom.sofathon.webcrawler.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class SpecificationWebCrawlerService {

    private static final int MAX_DEPTH = 1;
    private static String SPEC_URL = "http://specs.tomtomgroup.com/specification/ttom/ttom_23_a_final/index.html";
    private Set<String> links = new HashSet<>();
    private Map<String, String> map = new HashMap<>();

    public String getURL(final String heading) {
        String temp = heading.toUpperCase().replaceAll("\\s", "");
        return map.get(temp);
    }

    public void buildURLCache() {
        listAllEmbeddedURLs(SPEC_URL, 0);
        System.out.println(" ******************************************************** ");
        links.forEach(url -> buildCrawlingMap(url));
    }

    private void buildCrawlingMap(final String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements articleLinks = document.select("h1");
            for (Element article : articleLinks) {
                if (null != article.text() && article.text().length() > 0) {
                    String heading = article.text().toUpperCase().replaceAll("\\s", "");
                    map.put(heading, url);
                    System.out.println("Topic - " + heading + " ** " + " URL - " + url);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void listAllEmbeddedURLs(final String URL, int depth) {
        if ((!links.contains(URL) && (depth <= MAX_DEPTH))) {
            Elements linksOnPage = null;
            try {
                if (depth > 0) {
                    links.add(URL);
                }

                Document document = Jsoup.connect(URL).get();
                linksOnPage = document.select("a[href]");
                depth++;
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
            for (Element page : linksOnPage) {
                System.out.println(page.attr("abs:href"));
                listAllEmbeddedURLs(page.attr("abs:href"), depth);
            }
        }
    }

}
