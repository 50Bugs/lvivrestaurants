package com.restaurants.lvivrestaurants.parsing;

import com.restaurants.lvivrestaurants.domain.Restaurant;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ParsingData {

    public static ArrayList parseFromDlabComUA() {
        ArrayList<String> dlabComUAUrl = new ArrayList<>();
        dlabComUAUrl.add("https://www.dlab.com.ua/rubrika/303");
        for (int i = 0; i < 15; i++) {
            dlabComUAUrl.add("https://www.dlab.com.ua/rubrika/303?start_page=" + (i + 2));
        }
        ArrayList<Restaurant> RestaurantList = new ArrayList<>();

        for (String s : dlabComUAUrl) {
            try {
                Document document = Jsoup.connect(s).get();
                Elements restaurantsElements = document.getElementsByClass(
                        "d-flex flex-wrap bg-white rounded mb-3 border");

                for (Element e : restaurantsElements) {
                    String name = e.selectFirst("a").text();
                    String address = e.getElementsByClass(
                            "text-14 pl-4 position-relative map-pin-silhouette").text();
                    String phone = e.getElementsByClass(
                            "text-14 pl-4 position-relative cmp-list telephone-handset").text();
                    String email = e.getElementsByClass("text-14 pl-4 position-relative little-envelope").text();
                    String temp_url = e.getElementsByClass("text-14 pl-4 position-relative cursor").text();
                    //String info = e.getElementsByClass("text-14 pt-md-0").text().substring(12);

                    String info = "";
                    try {
                        Elements infoElements = e.getElementsByClass("text-14 pt-md-0");


                        for (Element infoElement : infoElements) {
                            if (infoElement.text().startsWith("Діяльність:")) {
                                info = infoElement.text().substring(11).trim();
                                break;
                            }
                        }
                    } catch (Exception ex) {
                        info = "";
                    }

                    Element imageElement = document.select("img.rounded").first();

                    String imageUrl;
                    try {
                        imageUrl = e.selectFirst("img").attr("src");

                    } catch (NullPointerException ex) {
                        imageUrl = "";
                    }

                    RestaurantList.add(new Restaurant(name, address, phone, email, temp_url, info, imageUrl));

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return RestaurantList;
    }


}