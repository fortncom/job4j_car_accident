package ru.job4j.accident.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.service.AccidentService;

@Controller
public class AccidentControl {
    private final AccidentService service;

    public AccidentControl(AccidentService service) {
        this.service = service;
    }

    @GetMapping(path = "/create")
    public String create() {
        return "accident/create";
    }

    @GetMapping(path = "/edit")
    public String edit(Model model, @RequestParam(name = "id") Integer id) {
        model.addAttribute("accident", service.get(id));
        return "accident/edit";
    }

    @PostMapping(path = "/save")
    public String save(@ModelAttribute Accident accident) {
        service.save(accident);
        return "redirect:/";
    }
}
