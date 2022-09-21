package ru.javarush.november.shott.cryptoanalizer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class BruteForce extends Cypher {
    private Path userOutputPath;


    public BruteForce(int key) {
        super(key);
        System.out.println("Throw me a encrypted file: ");
        inputPath = Path.of(console.nextLine());
        outputPath = Path.of("src/files/bruteForceDecryptionFile.txt");

        System.out.println("Throw me a output path for decrypted file: ");
        userOutputPath = Path.of(console.nextLine());

    }

    public void bruteForce() {
        if (Files.notExists(userOutputPath)) {
            try {
                Files.createFile(userOutputPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        for (int i = 0; i < alphabet.size(); i++) {
            encryptionKey += 1;
            decryption();

            int points = 0;
            try (BufferedReader bufferedReader = Files.newBufferedReader(outputPath, StandardCharsets.UTF_8);
                 BufferedWriter bufferedWriter = Files.newBufferedWriter(userOutputPath, StandardCharsets.UTF_8)) {
                String buffer = null;
                while (bufferedReader.ready()) {
                    buffer = bufferedReader.readLine();
                    char[] line = buffer.toCharArray();
                    for (int j = 0; j < line.length-3; j++) {
                        if (alphabet.contains(Character.toUpperCase(line[j])) && line[j + 1] == ' ' &&
                                alphabet.contains(Character.toUpperCase(line[j + 2]))) {
                            points += 1;
                        } else if (alphabet.contains(Character.toUpperCase(line[j])) && line[j + 1] == ',') {
                            points += 1;
                        } else if (line[line.length - 1] == '!' || line[line.length - 1] == '?'
                                || line[line.length - 1] == '.') {
                            points += 1;
                        }

                    }
                }

                if (points >= 100) {
                    bufferedWriter.write(buffer);
                }else {
                    bruteForce();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
    }

}
