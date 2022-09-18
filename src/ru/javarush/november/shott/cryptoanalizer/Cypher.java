package ru.javarush.november.shott.cryptoanalizer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cypher {
    protected int encryptionKey;

    protected Path inputPath;

    protected Path outputPath;

    protected Scanner console = new Scanner(System.in);

    protected List<Character> alphabet = new ArrayList<>(List.of('А', 'Б', 'В', 'Г', 'Д', 'Е',
            'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л',
            'М', 'Н', 'О', 'П', 'Р', 'С', 'Т',
            'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Э', 'Ю', 'Я',
            '.', ',', ' ', '"', ':', '-', '!', '?'));


    protected Cypher(int key) {
        encryptionKey = key;
    }

    public Cypher(int key, String function) {
        encryptionKey = key;

        if (function.equals("encrypt")) {
            System.out.println("Throw me a decrypted file: ");
            inputPath = Path.of(console.nextLine());

            System.out.println("Throw me a output path for encrypted file: ");
            outputPath = Path.of(console.nextLine());

        } else if (function.equals("decrypt")) {
            System.out.println("Throw me a encrypted file: ");
            inputPath = Path.of(console.nextLine());

            System.out.println("Throw me a output path for decrypted file: ");
            outputPath = Path.of(console.nextLine());

        }
    }


    public void encryption() {
        outputFileCreation(outputPath);

        try (BufferedWriter outputFile = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8);
             BufferedReader buffer = Files.newBufferedReader(inputPath, StandardCharsets.UTF_8)) {
            while (buffer.ready()) {
                char nextChar = (char) buffer.read();
                char upperNextChar = Character.toUpperCase(nextChar);
                if (alphabet.contains(upperNextChar)) {
                    if (alphabet.indexOf(upperNextChar) + encryptionKey > alphabet.size() - 1) {

                        List<Character> circularAlphabet = alphabet.subList(alphabet.indexOf(upperNextChar), alphabet.size());
                        for (int i = 0; i < alphabet.size() - circularAlphabet.size(); i++) {
                            circularAlphabet.add(alphabet.get(i));
                        }

                        Character encryptedChar = circularAlphabet.get(circularAlphabet.indexOf(upperNextChar) + encryptionKey);
                        outputFile.write(encryptedChar);

                    } else {
                        Character encryptedChar = alphabet.get(alphabet.indexOf(upperNextChar) + encryptionKey);
                        outputFile.write(encryptedChar);
                    }
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void decryption() {
        outputFileCreation(outputPath);

        try (BufferedWriter outputFile = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8);
             BufferedReader buffer = Files.newBufferedReader(inputPath, StandardCharsets.UTF_8)) {
            while (buffer.ready()) {
                char nextChar = (char) buffer.read();
                char upperNextChar = Character.toUpperCase(nextChar);
                if (alphabet.contains(upperNextChar)) {
                    if (alphabet.indexOf(upperNextChar) - encryptionKey < 0) {

                        List<Character> circularAlphabet = alphabet.subList(alphabet.indexOf(upperNextChar) + 1, alphabet.size());
                        circularAlphabet.addAll(alphabet.subList(0, alphabet.indexOf(upperNextChar) + 1));

                        Character encryptedChar = circularAlphabet.get(circularAlphabet.indexOf(upperNextChar) - encryptionKey);
                        outputFile.write(encryptedChar);
                    } else {
                        Character encryptedChar = alphabet.get(alphabet.indexOf(upperNextChar) - encryptionKey);
                        outputFile.write(encryptedChar);
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void outputFileCreation(Path outputFile){
        if (Files.notExists(outputFile)) {
            try {
                Files.createFile(outputPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}