package com.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main (String[] args) {
        FileUtils utils = new FileUtils();
        GeoApi geoApi = new GeoApi();

        List<FileModel> updatedDataModels = new ArrayList<>();
        List<FileModel> dataModels = utils.readFromCSVFile("dummyData.csv");

        for (FileModel model : dataModels) {
            try {
                String timeZone = geoApi.getTimeZoneByLongitudeLatitudeCoordinates(
                        String.valueOf(model.getLatitude()), String.valueOf(model.getLongitude()));
                model.setTimeZone(timeZone);
                updatedDataModels.add(model);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        utils.writeToCSVFile("dummyData.csv", updatedDataModels);
    }
}
