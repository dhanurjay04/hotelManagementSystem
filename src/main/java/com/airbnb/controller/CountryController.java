package com.airbnb.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/counties")
public class CountryController {

    @PostMapping("/addCountries")
    public  String addCountry(){

        return  "Done";


    }

}
