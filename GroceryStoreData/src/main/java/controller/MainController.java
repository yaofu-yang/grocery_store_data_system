package controller;
/*
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import model.*;

import java.util.ArrayList;
import java.util.List;

@RestController  // this class is a controller
@RequestMapping("/visits")  // the URL start with /visits
public class MainController {
    @GetMapping(path="/all")
    public @ResponseBody
    List<Visit> getAllVisits() {
        List<Visit> visits = new ArrayList<>();
        DataSet ds = new DataSet(); // Rong: I think this is the different dataset from database?
        return ds.getVisits();  // Putting this here for now as placeholder.
    }

    @GetMapping(path="/filter-{parameter}") //filter visits by parameter specified
    public List<Visit> getFridayVisits() {  // Placeholder method for now.
        return new ArrayList<>();  // Placeholder return value.
    }


    @GetMapping(path="/order-by-{parameter}") //presenting data by parameter specified
    public List<Visit> getVisitsOrderedByEntryTime() {
        return new ArrayList<>();
    }


    @PostMapping(path = "/add") //add a visit data
    public String addNewVisit() {
        return "Successful";
    }

    @PostMapping(path = "/add-by-form") //add a visit data via form
    public String addNewVisitForm() {
        return "Successful";
    }
}*/