package ru.job4j.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartUI {

    @Autowired
    private Store store;

    @Autowired
    private ConsoleInput input;

    public void add(String value) {
        store.add(value);
    }

    public void print() {
        String ask = input.ask("Вывести содержимое хранилища?");
        System.out.println(ask);
        for (String value : store.getAll()) {
            System.out.println(value);
        }
    }
}