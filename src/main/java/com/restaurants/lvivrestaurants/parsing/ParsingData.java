package com.restaurants.lvivrestaurants.parsing;

import com.restaurants.lvivrestaurants.domain.Restaurant;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

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
                    if (address == null) {
                        address = "";
                    }
                    String phone = e.getElementsByClass(
                            "text-14 pl-4 position-relative cmp-list telephone-handset").text();
                    if (phone == null) {
                        phone = "";
                    }
                    String email = e.getElementsByClass("text-14 pl-4 position-relative little-envelope").text();
                    if (email == null) {
                        email = "";
                    }
                    String temp_url = e.getElementsByClass("text-14 pl-4 position-relative cursor").text();
                    if (temp_url == null) {
                        temp_url = "";
                    }

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
                    if (info == null) {
                        info = "";
                    }

                    Element imageElement = document.select("img.rounded").first();

                    String imageUrl;
                    try {
                        imageUrl = e.selectFirst("img").attr("src");

                    } catch (NullPointerException ex) {
                        imageUrl = "";
                    }
                    if (imageUrl == null)
                        imageUrl = "";

                    RestaurantList.add(new Restaurant(name, address, phone, email, temp_url, info, imageUrl));

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return RestaurantList;
    }

    public static ArrayList parseFromGoogleMaps() {
        System.setProperty("webdriver.chrome.driver",
                "D:\\Main\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe"); // Вкажіть шлях до драйвера Chrome
        WebDriver driver = new ChromeDriver();
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        List<String> urlList= new ArrayList<>();
        String name = "";
        String rating = "";
        String averagePrice = "";
        String imageURL = "";
        String address = "";
        String menuURL = "";
        String website = "";
        String phone = "";
        String info = "";

        driver.get("https://www.google.com.ua/maps/search/ресторан+львів/@49.8318065,24.0162601,12.5z?hl=uk&entry=ttu");

        try {
            Thread.sleep(40000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        List<WebElement> linkElements = driver.findElements(By.cssSelector("a.hfpxzc"));
        int count = 0;
        for (WebElement element : linkElements) {
            urlList.add(element.getAttribute("href"));
        }

        System.out.println(urlList.toString());
        System.out.println(urlList.size());


        count = 0;
        for (String url : urlList) {
            try {
                driver.get(url);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                WebElement restaurantElement;

                try {
                    restaurantElement = driver.findElement(By.cssSelector("h1.DUwDvf.lfPIob"));
                    name = restaurantElement.getText();
                } catch (Exception e){
                    name = "";
                }

                try {
                    restaurantElement = driver.findElement(By.cssSelector("div.F7nice span[aria-hidden='true']"));
                    rating = restaurantElement.getText();
                } catch (Exception e){
                    rating = "";
                }

                try {
                    restaurantElement = driver.findElement(By.cssSelector("span[aria-label^='Ціни:']"));
                    averagePrice = restaurantElement.getText();
                } catch (Exception e){
                    averagePrice = "";
                }

                try {
                    restaurantElement = driver.findElement(By.cssSelector("img[decoding='async']"));
                    imageURL = restaurantElement.getAttribute("src");
                } catch (Exception e) {
                    imageURL = "";
                }

                try {
                    restaurantElement = driver.findElement(By.cssSelector("div.Io6YTe.fontBodyMedium.kR99db.fdkmkc"));
                    address = restaurantElement.getText();
                    //address = address.substring(0, address.length()-7);
                } catch (Exception e) {
                    address = "";
                }

                try {
                    restaurantElement = driver.findElement(By.cssSelector("a.CsEnBe"));
                    menuURL = restaurantElement.getAttribute("href");
                } catch (Exception e) {
                    menuURL = "";
                }

                try{
                    restaurantElement = driver.findElement(By.cssSelector("a.CsEnBe[aria-label^='Сайт:']"));
                    website = restaurantElement.getAttribute("href");
                } catch (Exception e) {
                    website = "";
                }

                try {
                    restaurantElement = driver.findElement(By.cssSelector("button.CsEnBe[aria-label^='Телефон:']"));
                    phone = restaurantElement.getAttribute("aria-label").replace("Телефон: ",
                            "").trim();
                } catch (Exception e){
                    phone = "";
                }

                try {
                    restaurantElement = driver.findElement(By.cssSelector("div.PYvSYb"));

                } catch (Exception e) {
                    info = "";
                }

                count++;
                System.out.println(count + "\n");
            } catch (Exception e) {
                System.out.println("Element not found!");
                System.out.println(url);
            }

            restaurants.add(new Restaurant(name, address, phone, website, imageURL, url, menuURL, rating,
                    averagePrice, info));
        }

        driver.quit();

        return restaurants;
    }

    public static ArrayList parseFromTomatoUA(){
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        ArrayList<String> tomatoUAUrl = new ArrayList<>();
        tomatoUAUrl.add("https://tomato.ua/ua/lviv/category/restaurant");
        for(int i = 0; i < 20; i++) {
            tomatoUAUrl.add("https://tomato.ua/ua/lviv/category/restaurant/p-" + (i + 2));
        }

        for (String s : tomatoUAUrl) {
            try {

                Document document = Jsoup.connect(s).get();
                Elements restaurantsElements = document.getElementsByClass(
                        "search_item search-item__box");


                for (Element e : restaurantsElements) {
                    String name = e.getElementsByClass("search-item__center-title desctop").text();
                    String restaurantUrl = e.select("a.search-item__center-title").attr("href");

                    Element scheduleElement = e.selectFirst("div.search-item__time-work span.close_open_badge");
                    String schedule = "";
                    if (scheduleElement != null) {
                        schedule = scheduleElement.parent().ownText();
                        schedule = schedule.replace("вс", "нд");
                    }

                    document = Jsoup.connect(restaurantUrl).get();

                    Element el = document.selectFirst("div.address");
                    String address = "";

                    if (el != null) {
                        address = el.text();
                        int lastSpace = address.lastIndexOf(" ");
                        if (lastSpace - 1 != address.lastIndexOf(",")) {
                            address = address.substring(0, lastSpace) + "," + address.substring(lastSpace);
                        }
                        if (address.indexOf("Львів") == 0){
                            address = address.replace("Львів", "м. Львів");
                        }
                    }

                    el = document.selectFirst("a.phone_call_mobile");
                    String phone = el != null ? el.text() : "";

                    el = document.selectFirst("div.rest_block_raiting");
                    String rating = el != null ? el.text().trim() : "";
                    if (rating.contains("?")) {
                        rating = "";
                    }

                    el = document.selectFirst("div.p-0.desctop_show");
                    String cuisine = "";
                    if (el != null) {
                        Elements cuisines = el.select("span[itemprop='servesCuisine']");
                        for (Element cuisineItem : cuisines) {
                            if (!cuisine.isEmpty()) {
                                cuisine += ", ";
                            }
                            cuisine += cuisineItem.text();
                        }
                    }

                    el = document.selectFirst("span.average_bill");
                    String averagePrice = "";
                    if (el != null) {
                        String dataVal = el.attr("data-val");
                        if (!dataVal.isEmpty()) {
                            int dollarCount = Integer.parseInt(dataVal);
                            averagePrice = "$".repeat(dollarCount);
                        }
                    }

                    el = document.selectFirst("div.text_content");
                    String info = "";
                    if (el != null) {
                        info = el.text();
                    }
                    if (!info.isEmpty()) {
                        info = info.replace("Опис ", "");
                    }

                    el = document.selectFirst("img.rest_img");
                    String imageURL = "";
                    if (el != null) {
                        imageURL = el.attr("src");
                    }

                    restaurants.add(new Restaurant(name, address, phone, info,
                            imageURL, rating, averagePrice, cuisine, schedule));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return restaurants;
    }
}