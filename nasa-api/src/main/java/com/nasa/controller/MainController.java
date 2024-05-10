package com.nasa.controller;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Controller
public class MainController {

	@GetMapping("/")
    public String index() {
        return "index";
    }
	
	@GetMapping("/image")
	public ModelAndView openImage() {
		return new ModelAndView("redirect:" + imgUrl());
	}


	@SuppressWarnings("deprecation")
    public URL imgUrl() {
        URL url = null;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.nasa.gov/planetary/apod?api_key=enjSdOo36CZz7MOBlK29JUmsJ1Do8ya9LHhcQ0Jb")).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());

            url = new URL(json.getString("hdurl"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //assert url != null;
        return url;
    }

}
