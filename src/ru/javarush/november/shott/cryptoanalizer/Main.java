package ru.javarush.november.shott.cryptoanalizer;

import java.util.Scanner;

public class Main {
    private static final String TYPE_IN_TO_CONTINUE = "Type 'encrypt/decrypt/brute' to continue or 'exit' to quit program: ";

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        System.out.println("Hello!");
        System.out.println(TYPE_IN_TO_CONTINUE);
        while (console.hasNextLine()) {
            String input = console.nextLine();

            if (input.equals("encrypt")) {
                System.out.println("Type encryption integer key");

                Cypher encrypt = new Cypher(console.nextInt(), input);
                encrypt.encryption();
                System.out.printf("Check out encrypted file in this directory: ", encrypt.outputPath);
                System.out.println(TYPE_IN_TO_CONTINUE);

            } else if (input.equals("decrypt")) {
                System.out.println("Type decryption integer key");

                Cypher decrypt = new Cypher(console.nextInt(), input);
                decrypt.decryption();
                System.out.printf("Check out decrypted file in this directory: ", decrypt.outputPath);
                System.out.println("\n" + TYPE_IN_TO_CONTINUE);

            } else if (input.equals("exit")) {
                break;

            } else if (input.equals("brute")) {
                BruteForce bruteForce = new BruteForce(0);
                bruteForce.bruteForce();
                System.out.println(TYPE_IN_TO_CONTINUE);
            } else {
                System.out.println("Type encrypt/decrypt/exit");

            }

        }
    }
}