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

                    // Оновлюємо порожні поля у r1 даними з r2
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
                    break;
                }
            }

            // Додаємо новий ресторан, якщо дубліката не знайдено
            if (!foundDuplicate) {
                list1.add(r2);
            }
        }
        return list1;
    }

    public static String normalizeAddress(String address){
        String normalized = address.toLowerCase();

        // Видаляємо поштовий індекс, якщо він є
        normalized = normalized.replaceAll(",?\\s*\\d{5}", "");

        // Видаляємо "Львівська область" або інші зайві частини
        normalized = normalized.replaceAll(",?\\s*львівська область", "");

        // Видаляємо "м. Львів" або "Львів", якщо вони є на початку чи в кінці
        normalized = normalized.replaceAll("^м\\.\\s*львів,?\\s*", "");
        normalized = normalized.replaceAll("^львів,?\\s*", "");
        normalized = normalized.replaceAll(",?\\s*львів$", "");

        // Змінюємо префікси "вулиця", "вул.", "площа", "пл.", "просп." на єдиний формат
        normalized = normalized.replaceAll("\\bвул\\.|вулиця\\b", "вул.");
        normalized = normalized.replaceAll("\\bпл\\.|площа\\b", "пл.");
        normalized = normalized.replaceAll("\\bпросп\\.|проспект\\b", "просп.");

        // Прибираємо зайві частини після "вул.", "пл." чи "просп.", якщо є більше одного пробілу до коми
        normalized = normalizeStreetName(normalized);

        // Видаляємо зайві уточнення в дужках, наприклад, "(підвал)"
        normalized = normalized.replaceAll("\\s*\\(.*?\\)", "");

        // Якщо адреса починається з номера, переносимо його в кінець
        if (normalized.matches("^\\d+,?\\s+.*")) {
            int firstCommaIndex = normalized.indexOf(",");
            if (firstCommaIndex != -1) {
                String number = normalized.substring(0, firstCommaIndex).trim();
                normalized = normalized.substring(firstCommaIndex + 1).trim() + ", " + number;
            }
        }


        // Очищаємо зайві пробіли та коми
        normalized = normalized.replaceAll(",\\s*,", ",").replaceAll("\\s+", " ");
        normalized = normalized.replaceAll(",\\s*$", "").trim();
        return normalized;
    }

    public static String normalizeStreetName(String address) {

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

}