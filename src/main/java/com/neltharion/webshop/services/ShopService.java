package com.neltharion.webshop.services;

import com.neltharion.webshop.models.ShopItem;
import com.neltharion.webshop.models.Type;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShopService {

    private static List<ShopItem> items;

    private String currency;

    public ShopService() {
        items = new ArrayList<>();
        items.add(new ShopItem("Running shoes", Type.CAS, "Nike running shoes for every day sport", 1000, 5));
        items.add(new ShopItem("Printer", Type.ELE, "Some HP printer that will print pages", 3000, 2));
        items.add(new ShopItem("Coca cola", Type.BAS, "0.5l standard coke", 25, 0));
        items.add(new ShopItem("Wokin", Type.BAS, "Chicken with fried rice and WOKIN sauce", 119, 100));
        items.add(new ShopItem("T-shirt", Type.CAS, "Blue with a corgi on a bike", 300, 1));
        currency = "czk";
    }

    public static List<ShopItem> getItems() {
        return items;
    }

    public static void setItems(List<ShopItem> items) {
        ShopService.items = items;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<ShopItem> getOnlyAvailable() {
        return items.stream()
                .filter(item -> item.getQuantityOfStock() > 0)
                .collect(Collectors.toList());
    }

    public List<ShopItem> getCheapestFirst() {
        return items.stream()
                .sorted(Comparator.comparingDouble(ShopItem::getPrice))
                .collect(Collectors.toList());
    }

    public List<ShopItem> search(String keyword) {
        return items.stream()
                .filter(item -> item.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                        item.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<ShopItem> containsNike() {
        return search("Nike");
    }

    public double getAverageStock() {
        OptionalDouble average = items.stream()
                .mapToLong(item -> item.getQuantityOfStock())
                .average();
        return average.orElse(0);
    }

    public List<ShopItem> getMostExpensive() {
        return items.stream()
                .sorted(Comparator.comparing(ShopItem::getPrice).reversed())
                .limit(1)
                .collect(Collectors.toList());
    }

    public List<ShopItem> getType(String type) {
        return items.stream()
                .filter(item -> item.getType().toString().toLowerCase().contains(type))
                .collect(Collectors.toList());
    }

    public List<ShopItem> getPrice(String currency) {
        switch (currency) {
            case "czk":
                if (this.currency.equals("eur")) {
                    for (ShopItem item : items) {
                        item.setPrice(Math.round(item.getPrice() * 24.5));
                    }
                    this.currency = "czk";
                }
                break;
            case "eur":
                if (this.currency.equals("czk")) {
                    for (ShopItem item : items) {
                        item.setPrice((double)Math.round((item.getPrice() / 24.5) * 100) / 100);
                    }
                    this.currency = "eur";
                }
                break;
        }
        return items;
    }

    public List<ShopItem> filterByPrice(Double value, String price) {

        return items.stream()
                .filter(obj -> {
                    if (price.equals("Above")) {
                        return obj.getPrice() >= value;
                    } else if (price.equals("Below")) {
                        return obj.getPrice() <= value;
                    } else if (price.equals("Exactly")) {
                        return obj.getPrice() == value;
                    }
                    return false;
                })
                .collect(Collectors.toList());

    }

}
