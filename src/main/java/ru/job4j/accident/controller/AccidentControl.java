package ru.job4j.accident.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.service.AccidentService;

@Controller
public class AccidentControl {
    private final AccidentService service;

    public AccidentControl(AccidentService service) {
        this.service = service;
    }

    @GetMapping(path = "/create")
    public String create(Model model) {
        model.addAttribute("types", service.getAllAccidentTypes());
        return "accident/create";
    }

    @GetMapping(path = "/edit")
    public String edit(Model model, @RequestParam(name = "id") Integer id) {
        model.addAttribute("accident", service.getAccident(id));
        return "accident/edit";
    }

    @PostMapping(path = "/save")
    public String save(@RequestParam(name = "id") Integer id,
                       @RequestParam(name = "name") String name,
                       @RequestParam(name = "text") String text,
                       @RequestParam(name = "address") String address,
                       @RequestParam(name = "type") Integer type) {
        Accident accident = Accident.of(id, name, text, address, AccidentType.of(type, null));
        service.save(accident);
        return "redirect:/";
    }
}
