package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private static final AtomicInteger ACCIDENT_ID = new AtomicInteger(0);
    private HashMap<Integer, Accident> accidents = new HashMap<>();

    public AccidentMem() {
        init();
    }

    public void init() {
        save(Accident.of("T3R4", "не пропустил авто выезжая со двора",
                "Горск, ул. Ленина, д.1"));
        save(Accident.of("GE3T1", "плохая видимость, густой туман",
                "Озёрск, ул. Московская, д.22"));
    }

    public Accident get(int id) {
        return accidents.get(id);
    }

    public Collection<Accident> getAll() {
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
}
