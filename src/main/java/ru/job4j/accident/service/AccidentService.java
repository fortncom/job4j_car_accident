package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentJdbcTemplate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class AccidentService {

    private AccidentJdbcTemplate repository;

    public AccidentService(AccidentJdbcTemplate  repository) {
        this.repository = repository;
    }

    public Collection<Accident> getAllAccidents() {
        return repository.getAllAccidents();
    }

    public Accident getAccident(int id) {
        return repository.getAccident(id);
    }

    public void save(Accident accident, String[] rulesId) {
        accident.setType(getAccidentType(accident.getType().getId()));
        Set<Rule> rules = new HashSet<>();
        for (String s : rulesId) {
            rules.add(getRule(Integer.parseInt(s)));
        }
        accident.setRules(rules);
        repository.save(accident);
    }

    public boolean remove(Accident accident) {
       return repository.remove(accident);
    }

    public Collection<AccidentType> getAllAccidentTypes() {
        return repository.getAllAccidentTypes();
    }

    public AccidentType getAccidentType(int id) {
        return repository.getAccidentType(id);
    }

    public void save(AccidentType type) {
        repository.save(type);
    }

    public boolean remove(AccidentType type) {
        return repository.remove(type);
    }

    public Collection<Rule> getAllRules() {
        return repository.getAllRules();
    }

    public Rule getRule(int id) {
        return repository.getRule(id);
    }

    public void save(Rule rule) {
        repository.save(rule);
    }

    public boolean remove(Rule rule) {
        return repository.remove(rule);
    }
}
