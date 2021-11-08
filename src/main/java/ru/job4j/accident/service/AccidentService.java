package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentRepository;
import ru.job4j.accident.repository.AccidentTypeRepository;
import ru.job4j.accident.repository.RuleRepository;

import java.util.*;

@Service
public class AccidentService {

    private final AccidentRepository repAccident;
    private final AccidentTypeRepository repType;
    private final RuleRepository repRule;

    public AccidentService(AccidentRepository repAccident,
                           AccidentTypeRepository repType, RuleRepository repRule) {
        this.repAccident = repAccident;
        this.repType = repType;
        this.repRule = repRule;
    }

    public Collection<Accident> getAllAccidents() {
        List<Accident> accidents = new ArrayList<>();
        repAccident.findAll().forEach(accidents::add);
        return accidents;
    }

    public Accident getAccident(int id) {
        return repAccident.findById(id).get();
    }

    @Transactional
    public void save(Accident accident, String[] rulesId) {
        accident.setType(getAccidentType(accident.getType().getId()));
        Set<Rule> rules = new HashSet<>();
        for (String s : rulesId) {
            rules.add(getRule(Integer.parseInt(s)));
        }
        accident.setRules(rules);
        repAccident.save(accident);
    }

    public boolean remove(Accident accident) {
        repAccident.delete(accident);
        return true;
    }

    public Collection<AccidentType> getAllAccidentTypes() {
        List<AccidentType> types = new ArrayList<>();
        repType.findAll().forEach(types::add);
        return types;
    }

    public AccidentType getAccidentType(int id) {
        return repType.findById(id).get();
    }

    @Transactional
    public void save(AccidentType type) {
        repType.save(type);
    }

    public boolean remove(AccidentType type) {
        repType.delete(type);
        return true;
    }

    public Collection<Rule> getAllRules() {
        List<Rule> rules = new ArrayList<>();
        repRule.findAll().forEach(rules::add);
        return rules;
    }

    public Rule getRule(int id) {
        return repRule.findById(id).get();
    }

    @Transactional
    public void save(Rule rule) {
        repRule.save(rule);
    }

    public boolean remove(Rule rule) {
        repRule.delete(rule);
        return true;
    }
}
