package com.eroad;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class FileUtils {
    private static final Logger LOGGER = Logger.getLogger(FileUtils.class.getName());

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public List<FileModel> readFromCSVFile(String filePath) {
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        List<FileModel> models = new ArrayList<>();

        File file = new File(getClass().getClassLoader().getResource(filePath).getFile());

        try (Stream<String> stream = Files.lines(Paths.get(file.toURI()))) {

            models = Arrays.stream(stream.toArray(String[]::new))
                    .map(line -> line.split(","))
                    .map(dataArr -> {
                        try {
                            return new FileModel(dateFormat.parse(dataArr[0]), dataArr[1], dataArr[2]);
                        } catch (ParseException e) {
                            LOGGER.log(Level.SEVERE, e.getMessage());
                        }
                        return null;
                    })
                    .collect(toList());

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return models;
    }

    public void writeToCSVFile(String filePath, List<FileModel> updatedDataModels) {

        List<String> dataToWrite = new ArrayList<>();
        for (FileModel model : updatedDataModels) {
            dataToWrite.add(constructWritableData(model));
        }

        File file = new File(getClass().getClassLoader().getResource(filePath).getFile());
        Path path = Paths.get(file.toURI());

        try (BufferedWriter writer = Files.newBufferedWriter(path))
        {
            dataToWrite.stream().forEach(line -> {
                try {
                    writer.write(line);
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, e.getMessage());
                }
            });
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    private String constructWritableData(FileModel model) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        StringBuffer sb = new StringBuffer();
        sb.append(dateFormat.format(model.getUtcDateTime()) + ",");
        sb.append(model.getLatitude() + ",");
        sb.append(model.getLongitude() + ",");
        sb.append(model.getTimeZone() + ",");

        formatter.setTimeZone(TimeZone.getTimeZone(model.getTimeZone()));
        sb.append(formatter.format(model.getUtcDateTime()) + "\n");
        return sb.toString();
    }
}
