package ru.javarush.november.shott.cryptoanalizer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BruteForce extends Cypher {
    protected Path userOutputPath;

    private HashMap<Integer, String> pointsAndText = new HashMap<>();

    public BruteForce(int key) {
        super(key);
        System.out.println("Throw me a encrypted file: ");
        inputPath = Path.of(console.nextLine());

        outputPath = Path.of("src/resources/bruteForceDecryptionFile.txt");

        System.out.println("Throw me a output path for decrypted file: ");
        userOutputPath = Path.of(console.nextLine());

    }

    public void bruteForce() {
        outputFileCreation(userOutputPath);

        for (int i = 0; i < alphabet.size(); i++) {
            encryptionKey += 1;
            decryption();

            int points = 0;
            try (BufferedReader bufferedReader = Files.newBufferedReader(outputPath, StandardCharsets.UTF_8)) {
                String buffer = null;
                while (bufferedReader.ready()) {
                    buffer = bufferedReader.readLine();
                    char[] line = buffer.toCharArray();
                    for (int j = 0; j < line.length - 4; j++) {
                        if (alphabet.contains(line[j]) && line[j + 1] == ' ' &&
                                alphabet.contains(line[j + 2])) {
                            points += 1;
                        } else if (alphabet.contains(line[j]) && line[j + 1] == ',') {
                            points += 1;
                        } else if (line[j + 1] == ',' && line[j + 2] == ' '
                                && line[j + 3] == 'Н' && line[j + 3] == 'О') {
                            points += 1;
                        } else if (line[j] == ' ' && line[j + 1] == 'В' && line[j + 2] == ' ') {
                            points += 1;
                        } else if (line[j] == ' ' && line[j + 1] == 'Н' && line[j + 2] == 'А'
                                && line[j + 3] == ' ') {
                            points += 1;
                        } else if (line[j] == ',' && line[j + 1] == ' ' && line[j + 2] == 'Ч'
                                && line[j + 3] == 'Т') {
                            points += 1;
                        } else if (line[j] == ' ' && line[j + 1] == ',' && line[j + 2] == ' ') {
                            points += 1;
                        }
                    }
                }

                pointsAndText.put(points, buffer);
                bruteForce();

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (IndexOutOfBoundsException e) {
                hashMapValueSearch();
                return;
            }

        }
    }

    private void hashMapValueSearch(){
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(userOutputPath, StandardCharsets.UTF_8)) {
            int maxValueInMap = Collections.max(pointsAndText.keySet());
            for (Map.Entry<Integer, String> entry : pointsAndText.entrySet()) {
                if (entry.getKey() == maxValueInMap) {
                    bufferedWriter.write(entry.getValue());
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
