package com.example.frontend.controller;

import com.example.frontend.models.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping(path = "items")
@Service
public class ItemController {
    private final RestTemplate restTemplate;

    @Value("${api_base_url:http://localhost:8080/}")
    private String apiBaseUrl;

    public ItemController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Item[] getItems() {
        String url = apiBaseUrl + "items";
        return this.restTemplate.getForObject(url, Item[].class);
    }

    @RequestMapping()
    public String allItems(Model model) {
        Iterable<Item> items = List.of(getItems());
        model.addAttribute("allItems", items);
        model.addAttribute("nameTitle", "name");
        model.addAttribute("itemTitle", "All items");
        return "allItems";
    }
}
