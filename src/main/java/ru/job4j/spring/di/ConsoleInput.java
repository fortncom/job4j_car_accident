package ru.job4j.spring.di;

import java.util.Scanner;

public class ConsoleInput {

    private Scanner scanner = new Scanner(System.in);

    public String ask(String question) {
        System.out.println(question);
        return scanner.nextLine();
    }
}
