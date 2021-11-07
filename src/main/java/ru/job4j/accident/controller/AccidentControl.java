package ru.job4j.accident.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.service.AccidentService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AccidentControl {
    private final AccidentService service;

    public AccidentControl(AccidentService service) {
        this.service = service;
    }

    @GetMapping(path = "/create")
    public String create(Model model) {
        model.addAttribute("types", service.getAllAccidentTypes());
        model.addAttribute("rules", service.getAllRules());
        return "accident/create";
    }

    @GetMapping(path = "/edit")
    public String edit(Model model, @RequestParam(name = "id") Integer id) {
        model.addAttribute("accident", service.getAccident(id));
        model.addAttribute("types", service.getAllAccidentTypes());
        model.addAttribute("rules", service.getAllRules());
        return "accident/edit";
    }

    @PostMapping(path = "/save")
    public String save(@ModelAttribute Accident accident,
                       HttpServletRequest req) {
        service.save(accident, req.getParameterValues("rIds"));
        return "redirect:/";
    }
}
