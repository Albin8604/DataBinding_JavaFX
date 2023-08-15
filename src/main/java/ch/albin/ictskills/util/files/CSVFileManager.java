package ch.albin.ictskills.util.files;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

//https://www.baeldung.com/opencsv
public class CSVFileManager<T> implements FileManager<T> {
    private static final char SEPARATOR = ',';
    private final Class<T> tClass;

    public CSVFileManager(Class<T> tClass) {
        this.tClass = tClass;
    }
    @Override
    public List<T> read(File file) {
        List<T> objList = new ArrayList<>();

        CsvToBeanBuilder<T> beanBuilder;
        try {
            beanBuilder = new CsvToBeanBuilder<>(
                    new InputStreamReader(new FileInputStream(file))
            );

            beanBuilder.withType(tClass);
            beanBuilder.withSeparator(SEPARATOR);

            // build methods returns a list of Beans

            beanBuilder.build().parse().forEach(e -> {
                System.out.println(e);
                objList.add(e);
            });

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return objList;
    }

    @Override
    public void write(List<T> data, String fileName) {

        try (Writer writer  = new FileWriter(fileName)) {

            StatefulBeanToCsv<T> statefulBeanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withQuotechar('\"')
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();

            statefulBeanToCsv.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvRequiredFieldEmptyException e) {
            throw new RuntimeException(e);
        } catch (CsvDataTypeMismatchException e) {
            throw new RuntimeException(e);
        }

    }
}
