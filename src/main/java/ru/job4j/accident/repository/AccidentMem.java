package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private static final AtomicInteger ACCIDENT_ID = new AtomicInteger(0);
    private static final AtomicInteger ACCIDENT_TYPE_ID = new AtomicInteger(0);
    private static final AtomicInteger RULE_ID = new AtomicInteger(0);
    private HashMap<Integer, Accident> accidents = new HashMap<>();
    private HashMap<Integer, AccidentType> types = new HashMap<>();
    private HashMap<Integer, Rule> rules = new HashMap<>();

    public AccidentMem() {
        init();
    }

    public void init() {
        save(AccidentType.of(0, "Две машины"));
        save(AccidentType.of(0, "Машина и человек"));
        save(AccidentType.of(0, "Машина и велосипед"));
        save(Rule.of(0, "Статья. 1"));
        save(Rule.of(0, "Статья. 2"));
        save(Rule.of(0, "Статья. 3"));
        save(Accident.of(0, "T3R4", "не пропустил авто выезжая со двора",
                "Горск, ул. Ленина, д.1",
                getAccidentType(1), Set.of(getRule(1), getRule(2))));
        save(Accident.of(0, "GE3T1", "плохая видимость, густой туман",
                "Озёрск, ул. Московская, д.22",
                getAccidentType(2), Set.of(getRule(2), getRule(3))));
    }

    public Accident getAccident(int id) {
        return accidents.get(id);
    }

    public Collection<Accident> getAllAccidents() {
        return accidents.values();
    }

    public Accident save(Accident accident) {
        Accident rsl;
        if (accident.getId() == 0) {
            accident.setId(ACCIDENT_ID.incrementAndGet());
          rsl = add(accident);
        } else {
          rsl = replace(accident);
        }
        return rsl;
    }

    public boolean remove(Accident accident) {
       return accidents.remove(accident.getId(), accident);
    }

    private Accident add(Accident accident) {
        return accidents.putIfAbsent(accident.getId(), accident);
    }

    private Accident replace(Accident accident) {
        return accidents.replace(accident.getId(), accident);
    }

    public AccidentType getAccidentType(int id) {
        return types.get(id);
    }

    public Collection<AccidentType> getAllAccidentTypes() {
        return types.values();
    }

    public AccidentType save(AccidentType type) {
        AccidentType rsl;
        if (type.getId() == 0) {
            type.setId(ACCIDENT_TYPE_ID.incrementAndGet());
            rsl = add(type);
        } else {
            rsl = replace(type);
        }
        return rsl;
    }

    public boolean remove(AccidentType type) {
        return types.remove(type.getId(), type);
    }

    private AccidentType add(AccidentType type) {
        return types.putIfAbsent(type.getId(), type);
    }

    private AccidentType replace(AccidentType type) {
        return types.replace(type.getId(), type);
    }

    public Rule getRule(int id) {
        return rules.get(id);
    }

    public Collection<Rule> getAllRules() {
        return rules.values();
    }

    public Rule save(Rule rule) {
        Rule rsl;
        if (rule.getId() == 0) {
            rule.setId(RULE_ID.incrementAndGet());
            rsl = add(rule);
        } else {
            rsl = replace(rule);
        }
        return rsl;
    }

    public boolean remove(Rule rule) {
        return rules.remove(rule.getId(), rule);
    }

    private Rule add(Rule rule) {
        return rules.putIfAbsent(rule.getId(), rule);
    }

    private Rule replace(Rule rule) {
        return rules.replace(rule.getId(), rule);
    }
}
