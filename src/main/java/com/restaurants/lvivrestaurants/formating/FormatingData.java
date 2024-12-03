package com.restaurants.lvivrestaurants.formating;
import com.restaurants.lvivrestaurants.domain.Restaurant;

import java.util.ArrayList;

public class FormatingData {

    public static ArrayList<Restaurant> mergeRestaurantLists(
            ArrayList<Restaurant> list1, ArrayList<Restaurant> list2) {

        for (Restaurant r2 : list2) {
            boolean foundDuplicate = false;

            for (Restaurant r1 : list1) {
                if (normalizeAddress(r1.getAddress()).equals(normalizeAddress(r2.getAddress()))) {
                    foundDuplicate = true;

                    if (r1.getPhone() == null || r1.getPhone().isEmpty()) {
                        r1.setPhone(r2.getPhone());
                    }
                    if (r1.getEmail() == null || r1.getEmail().isEmpty()) {
                        r1.setEmail(r2.getEmail());
                    }
                    if (r1.getWebsiteURL() == null || r1.getWebsiteURL().isEmpty()) {
                        r1.setWebsiteURL(r2.getWebsiteURL());
                    }
                    if (r1.getImageURL() == null || r1.getImageURL().isEmpty()) {
                        r1.setImageURL(r2.getImageURL());
                    }
                    if (r1.getInfo() == null || r1.getInfo().isEmpty()) {
                        r1.setInfo(r2.getInfo());
                    }
                    if (r1.getGoogleMapsURL() == null || r1.getGoogleMapsURL().isEmpty()) {
                        r1.setGoogleMapsURL(r2.getGoogleMapsURL());
                    }
                    if (r1.getMenuURL() == null || r1.getMenuURL().isEmpty()) {
                        r1.setMenuURL(r2.getMenuURL());
                    }
                    if (r1.getRating() == null || r1.getRating().isEmpty()) {
                        r1.setRating(r2.getRating());
                    }
                    if (r1.getAveragePrice() == null || r1.getAveragePrice().isEmpty()) {
                        r1.setAveragePrice(r2.getAveragePrice());
                    }
                    if (r1.getCuisine() == null || r1.getCuisine().isEmpty()) {
                        r1.setCuisine(r2.getCuisine());
                    }
                    if (r1.getSchedule() == null || r1.getSchedule().isEmpty()) {
                        r1.setSchedule(r2.getSchedule());
                    }
                    break;
                }
            }

            if (!foundDuplicate) {
                list1.add(r2);
            }
        }
        return list1;
    }

    public static String normalizeAddress(String address){
        String normalized = address.toLowerCase();

        // видалення поштового індексу, якщо він є
        normalized = normalized.replaceAll(",?\\s*\\d{5}", "");

        // видалення "Львівська область" або інші зайві частини
        normalized = normalized.replaceAll(",?\\s*львівська область", "");

        // видалення "м. Львів" або "Львів", якщо вони є на початку чи в кінці
        normalized = normalized.replaceAll("^м\\.\\s*львів,?\\s*", "");
        normalized = normalized.replaceAll("^львів,?\\s*", "");
        normalized = normalized.replaceAll(",?\\s*львів$", "");

        // зміна префіксів "вулиця", "вул.", "площа", "пл.", "просп." на єдиний формат
        normalized = normalized.replaceAll("\\bвул\\.|вулиця\\b", "вул.");
        normalized = normalized.replaceAll("\\bпл\\.|площа\\b", "пл.");
        normalized = normalized.replaceAll("\\b(просп\\.|проспект|пр\\.)\\b", "просп.");

        // видалення зайвих частин після "вул.", "пл." чи "просп.", якщо є більше одного пробілу до коми
        normalized = normalizeStreetName(normalized);

        // видалення уточнень про поверхи, квартири
        normalized = normalized.replaceAll("\\s*,?\\s*пов\\.?\\s*\\d+", ""); // "пов. 2" -> ""
        normalized = normalized.replaceAll("\\s*,?\\s*к\\.\\s*\\d+", "");   // "к. 8" -> ""

        // видалення зайвих уточнень в дужках, наприклад, "(підвал)"
        normalized = normalized.replaceAll("\\s*\\(.*?\\)", "");

        // якщо адреса починається з номера, переносить його в кінець
        if (normalized.matches("^\\d+,?\\s+.*")) {
            int firstCommaIndex = normalized.indexOf(",");
            if (firstCommaIndex != -1) {
                String number = normalized.substring(0, firstCommaIndex).trim();
                normalized = normalized.substring(firstCommaIndex + 1).trim() + ", " + number;
            }
        }

        // зайві пробіли та коми
        normalized = normalized.replaceAll(",\\s*,", ",").replaceAll("\\s+", " ");
        normalized = normalized.replaceAll(",\\s*$", "").trim();

        normalized = removeLettersFromHouseNumber(normalized);
        return normalized;
    }

    private static String normalizeStreetName(String address) {

        if (address == null || address.isEmpty())
            return address;
        int firstSpace = address.indexOf(" ");
        int secondSpace = address.indexOf(" ", firstSpace+1);
        int commaIndex = address.indexOf(",");
        if (firstSpace == -1 || secondSpace == -1) {
            return address;
        }
        if (secondSpace > commaIndex) {
            return address;
        } else {
            String address1 = new String(address.substring(secondSpace));
            String address2 = new String(address.substring(0, firstSpace));
            return address2 + address1;
        }

    }

    private static String removeLettersFromHouseNumber(String address) {
        if (address == null || address.isEmpty()) {
            return address; // Повернути оригінальний адрес, якщо він null або порожній
        }

        // Перевірка на наявність коми
        if (!address.contains(",")) {
            return address; // Якщо коми немає, повернути оригінальний адрес
        }

        // Розбити адресу на частини
        String[] parts = address.split(",");

        // Перевірка на наявність хоча б двох частин (вулиця і номер будинку)
        if (parts.length < 2) {
            return address; // Повернути оригінальний адрес, якщо формат неправильний
        }

        String street = parts[0].trim(); // Вулиця
        String houseNumber = parts[1].trim(); // Номер будинку

        // Видалити літери з номера будинку
        houseNumber = houseNumber.replaceAll("[^\\d]", ""); // Залишити лише цифри

        return street + ", " + houseNumber;
    }

}
