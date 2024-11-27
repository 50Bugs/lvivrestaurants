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
                "D:\\Main\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
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
            Thread.sleep(50000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        // Отримуємо список елементів на сторінці
        List<WebElement> linkElements = driver.findElements(By.cssSelector("a.hfpxzc"));
        int count = 0;
        for (WebElement element : linkElements) {
            urlList.add(element.getAttribute("href"));
        }

        System.out.println(urlList.toString());
        System.out.println(urlList.size());


        count = 0;
        for (String url : urlList) {
            //driver.get(url);
            try {
                driver.get(url);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                WebElement restaurantElement;

                //Назва ресторану
                try {
                    restaurantElement = driver.findElement(By.cssSelector("h1.DUwDvf.lfPIob"));
                    name = restaurantElement.getText();
                } catch (Exception e){
                    name = "";
                }

                //Рейтинг
                try {
                    restaurantElement = driver.findElement(By.cssSelector("div.F7nice span[aria-hidden='true']"));
                    rating = restaurantElement.getText();
                } catch (Exception e){
                    rating = "";
                    //e.printStackTrace();
                }
                if (rating == null) {
                    rating = "";
                }

                //Середня вартість чеку
                try {
                    restaurantElement = driver.findElement(By.cssSelector("span[aria-label^='Ціни:']"));
                    averagePrice = restaurantElement.getText();
                } catch (Exception e){
                    averagePrice = "";
                }
                if (averagePrice == null) {
                    averagePrice = "";
                }

                //Посилання на зображення
                try {
                    restaurantElement = driver.findElement(By.cssSelector("img[decoding='async']"));
                    imageURL = restaurantElement.getAttribute("src");
                } catch (Exception e) {
                    imageURL = "";
                }
                if (imageURL == null) {
                    imageURL = "";
                }

                //Адреса
                try {
                    restaurantElement = driver.findElement(By.cssSelector("div.Io6YTe.fontBodyMedium.kR99db.fdkmkc"));
                    address = restaurantElement.getText();
                } catch (Exception e) {
                    address = "";
                }
                if (address == null) {
                    address = "";
                }

                //Меню
                try {
                    restaurantElement = driver.findElement(By.cssSelector("a.CsEnBe"));
                    menuURL = restaurantElement.getAttribute("href");
                } catch (Exception e) {
                    menuURL = "";
                }
                if (menuURL == null) {
                    menuURL = "";
                }

                //Вебсайт
                try{
                    restaurantElement = driver.findElement(By.cssSelector("a.CsEnBe[aria-label^='Сайт:']"));
                    website = restaurantElement.getAttribute("href");
                } catch (Exception e) {
                    website = "";
                }
                if (website == null) {
                    website = "";
                }

                // Телефон
                try {
                    restaurantElement = driver.findElement(By.cssSelector("button.CsEnBe[aria-label^='Телефон:']"));
                    phone = restaurantElement.getAttribute("aria-label").replace("Телефон: ", "").trim();
                } catch (Exception e){
                    phone = "";
                }
                if (phone == null) {
                    phone = "";
                }

                // Опис ресторану
                try {
                    restaurantElement = driver.findElement(By.cssSelector("div.PYvSYb"));
                    info = restaurantElement.getText().trim(); // Отримуємо текстовий опис ресторану
                } catch (Exception e) {
                    info = "";
                }
                if (info == null) {
                    info = "";
                }

                count++;
                System.out.println(count + "\n");
            } catch (Exception e) {
                System.out.println("Element not found!");
                System.out.println(url);
            }

            restaurants.add(new Restaurant(name, address, phone, website, imageURL, url, menuURL, rating, averagePrice, info));
        }

        driver.quit();

        return restaurants;
    }
}