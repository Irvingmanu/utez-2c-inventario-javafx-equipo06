package com.example.integradora.repositories;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileRepository {

    private final Path pathFile = Paths.get("archivo", "productos.csv");

    public void ensureFile() throws IOException {
        if (Files.notExists(pathFile)) {
            Files.createFile(pathFile);
        }
    }

    public List<String> readAllLines() throws IOException {
        ensureFile();
        return Files.readAllLines(pathFile, StandardCharsets.UTF_8);
    }

    public void addNewLine(String line) throws IOException {
        ensureFile();
        Files.writeString(pathFile,
                line + System.lineSeparator(), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
    }

    public void saveFile(List<String> lines) throws IOException {
        ensureFile();
        Files.write(pathFile, lines, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
    }

}


