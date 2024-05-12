package com.nasa.controller;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {

    private final String NASA_URL = "https://api.nasa.gov/planetary/apod?api_key=enjSdOo36CZz7MOBlK29JUmsJ1Do8ya9LHhcQ0Jb";

	@GetMapping("/")
    public String index(Model model) {
        model.addAttribute("url_map", content());
        return "index";
    }

	@SuppressWarnings("deprecation")
    public Map<String, URL> content() {
        Map<String, URL> mapa = new HashMap<>();
        String mediaType;
        URL url = null;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(NASA_URL)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());

            mediaType = json.getString("media_type");
            url = new URL(json.getString("url"));

            mapa.put(mediaType, url);

        } catch (Exception e) {
            e.fillInStackTrace();
        }

        return mapa;
    }

}
