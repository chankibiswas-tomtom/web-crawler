package com.tomtom.sofathon.webcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {

    private static final int MAX_DEPTH = 1;
    private static String SPEC_URL = "https://confluence.tomtomgroup.com/display/DOCINF/How+to+Edit+Features+in+Cartopia";
    /*private static String SPEC_URL =
        "http://specs.tomtomgroup.com/specification/ttom/ttom_23_a_final/common_spec/theme_roads_and_ferries/form_of_way"
        + "/form_of_way.html";*/
    private static Set<String> links = new HashSet<>();
    private static Map<String, String> map = new HashMap<>();

    public static void main(String[] s) {
        listAllEmbeddedURLs(SPEC_URL, 0);
        //System.out.println(" ******************************************************** ");
        //links.forEach(url -> buildCrawlingMap(url));
    }

    private static void buildCrawlingMap(final String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements articleLinks = document.select("h1");
            for (Element article : articleLinks) {
                if (null != article.text() && article.text().length() > 0) {
                    map.put(article.text(), url);
                    System.out.println("Topic - " + article.text() + " ** " + " URL - " + url);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void listAllEmbeddedURLs(final String URL, int depth) {
        if ((!links.contains(URL) && (depth <= MAX_DEPTH))) {
            try {
                if (depth > 1) {
                    System.out.println(">> Depth: " + depth + " [" + URL + "]");
                    links.add(URL);
                }

                Document document = Jsoup.connect(URL).get();
                Elements linksOnPage = document.select("a[href]");

                depth++;
                for (Element page : linksOnPage) {
                    listAllEmbeddedURLs(page.attr("abs:href"), depth);
                }
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }

}
