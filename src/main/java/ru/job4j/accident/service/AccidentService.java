package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.repository.AccidentMem;

import java.util.Collection;

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
}
