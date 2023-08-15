package ch.albin.ictskills.util.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVFileManagerHandMade<T> implements FileManager<T>{

    private final Class<T> tClass;

    public CSVFileManagerHandMade(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public List<T> read(File file) {
        List<T> objList = new ArrayList<>();
        Scanner input = null;

        try {
            input = new Scanner(file);

            while (input.hasNextLine()) {
                String line = input.nextLine();

                //todo do your thing
                System.out.println(line);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            if (input != null){
                input.close();
            }
        }

        return objList;
    }

    @Override
    public void write(List<T> data, String path) {
        BufferedWriter writer = null;
        StringBuilder stringToWrite = new StringBuilder();

        //todo header
        stringToWrite.append("test");

        //todo data
        for (T oneData : data) {
            stringToWrite.append("TODO");
        }

        try {
            writer = new BufferedWriter(new FileWriter(path));
            writer.write(stringToWrite.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
