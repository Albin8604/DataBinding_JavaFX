package ch.albin.ictskills.util.files;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class JSONFileManager<T> implements FileManager<T> {

    private final Class<T> tClass;

    public JSONFileManager(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public List<T> read(File file) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<T> objList = null;

        try {
            byte[] jsonData = Files.readAllBytes(
                    file.toPath()
            );

            objList = objectMapper.readValue(jsonData, objectMapper.getTypeFactory().constructCollectionType(List.class, tClass));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return objList;
    }

    @Override
    public void write(List<T> data, String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writeValue(new File(filePath),data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getContentFromFileAsString(File file) throws IOException {
        List<String> allLines = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        Files.readAllLines(file.toPath());

        stringBuilder.append(allLines);

        return stringBuilder.toString();
    }
}
