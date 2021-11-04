package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentMem;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class AccidentService {

    private AccidentMem mem;

    public AccidentService(AccidentMem mem) {
        this.mem = mem;
    }

    public Collection<Accident> getAllAccidents() {
        return mem.getAllAccidents();
    }

    public Accident getAccident(int id) {
        return mem.getAccident(id);
    }

    public void save(Accident accident) {
        accident.setType(getAccidentType(accident.getType().getId()));
        Set<Rule> rules = new HashSet<>();
        for (Rule rule : accident.getRules()) {
            rules.add(getRule(rule.getId()));
        }
        accident.setRules(rules);
        mem.save(accident);
    }

    public boolean remove(Accident accident) {
       return mem.remove(accident);
    }

    public Collection<AccidentType> getAllAccidentTypes() {
        return mem.getAllAccidentTypes();
    }

    public AccidentType getAccidentType(int id) {
        return mem.getAccidentType(id);
    }

    public void save(AccidentType type) {
        mem.save(type);
    }

    public boolean remove(AccidentType type) {
        return mem.remove(type);
    }

    public Collection<Rule> getAllRules() {
        return mem.getAllRules();
    }

    public Rule getRule(int id) {
        return mem.getRule(id);
    }

    public void save(Rule rule) {
        mem.save(rule);
    }

    public boolean remove(Rule rule) {
        return mem.remove(rule);
    }
}
