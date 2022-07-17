package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class App {
    private static final String city = "almaty";
    private static final String mainPath = "https://sxodim.com";
    private static final String folderPath = "C:\\Users\\akzam\\OneDrive\\Desktop\\photo";

    private static Document getPage(int number) throws IOException {
        String url = mainPath + "/" + city + "/tickets?page=" + number;
        return Jsoup.parse(new URL(url),1500);
    }

    public static void main( String[] args ) throws IOException {
        getAllElements();
    }

    public static void getAllElements() throws IOException {
        List<Event> events = new ArrayList<>();

        for (int i = 1; true; i++) {
            Element itemsBlock = getPage(i).select("div[class=impression-items]").first();
            Elements titlesWithHtmlTags = itemsBlock.select("a[class=impression-card-title]");
            Elements descriptionsWithHtmlTags = itemsBlock.select("div[class=impression-card-info]");
            Elements images = itemsBlock.select("img");
            if (titlesWithHtmlTags.size() == 0) break;

            for (int j = 0; j < titlesWithHtmlTags.size(); j++) {
                events.add(new Event(
                        titlesWithHtmlTags.get(j).text(),
                        descriptionsWithHtmlTags.get(j).text(),
                        mainPath + images.get(j).attr("src")
                ));
            }
        }

        events.stream().forEach(System.out::println);

        int n = 0;
        for (Event event : events) {
            try (InputStream in = new URL(event.getImage()).openStream()) {
                Files.copy(in, Paths.get(folderPath + "\\" + n + ".jpg"));
                n++;
            }
        }
    }
}
