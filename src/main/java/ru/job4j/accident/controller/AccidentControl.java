package ru.job4j.accident.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.service.AccidentService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

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
        model.addAttribute("rules", service.getAllRules());
        return "accident/edit";
    }

    @PostMapping(path = "/save")
    public String save(@RequestParam(name = "id") Integer id,
                       @RequestParam(name = "name") String name,
                       @RequestParam(name = "text") String text,
                       @RequestParam(name = "address") String address,
                       @RequestParam(name = "type") Integer type,
                       HttpServletRequest req) {
        String[] rIds = req.getParameterValues("rIds");
        Set<Rule> rules = new HashSet<>();
        for (String rId : rIds) {
            rules.add(Rule.of(Integer.parseInt(rId), null));
        }
        Accident accident = Accident.of(id, name, text, address,
                AccidentType.of(type, null), rules);
        service.save(accident);
        return "redirect:/";
    }
}
