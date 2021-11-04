package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repository.AccidentMem;

import java.util.Collection;

@Service
public class AccidentService {

    private AccidentMem mem;

    public AccidentService(AccidentMem mem) {
        this.mem = mem;
    }

    public Collection<Accident> getAllAccidents() {
        return mem.getAll();
    }

    public Accident get(int id) {
        return mem.get(id);
    }

    public void save(Accident accident) {
        mem.save(accident);
    }

    public boolean remove(Accident accident) {
       return mem.remove(accident);
    }
}
