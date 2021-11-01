package ru.job4j.spring.di;

public class StartUI {

    private Store store;
    private ConsoleInput input;

    public StartUI(Store store, ConsoleInput input) {
        this.store = store;
        this.input = input;
    }

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