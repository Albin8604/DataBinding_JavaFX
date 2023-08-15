package ch.albin.ictskills.test.util.files;

import ch.albin.ictskills.test.model.TestPerson;
import ch.albin.ictskills.util.files.CSVFileManager;
import ch.albin.ictskills.util.files.JSONFileManager;

import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class TestFileManager {

    CSVFileManager<TestPerson> csvFileManager = new CSVFileManager<>(TestPerson.class);
    JSONFileManager<TestPerson> jsonFileManager = new JSONFileManager<>(TestPerson.class);

    List<TestPerson> TEST_PERSONS = List.of(
            new TestPerson(
                    1
                    , "Person1"
                    , new String[]{"Test2", "Test4"}
                    , List.of("Test1", "Test2")
                    , LocalDateTime.of(
                    2005, 4, 7, 7, 15
            )
                    , LocalTime.now()
            ),
            new TestPerson(
                    2
                    , "Person2"
                    , new String[]{"Test1", "Test2"}
                    , List.of("Test1", "Test2")
                    , LocalDateTime.of(
                    2005, 4, 7, 7, 15
            )
                    , LocalTime.now()
            ),
            new TestPerson(
                    3
                    , "Person3"
                    , new String[]{"Test1", "Test2"}
                    , List.of("Test1", "Test2")
                    , LocalDateTime.of(
                    2005, 4, 7, 7, 15
            )
                    , LocalTime.now()
            ),
            new TestPerson(
                    4
                    , "Person4"
                    , new String[]{"Test1", "Test2"}
                    , List.of("Test1", "Test2")
                    , LocalDateTime.of(
                    2005, 4, 7, 7, 15
            )
                    , LocalTime.now()
            ),
            new TestPerson(
                    5
                    , "Person5"
                    , new String[]{"Test1", "Test2"}
                    , List.of("Test1", "Test2")
                    , LocalDateTime.of(
                    2005, 4, 7, 7, 15
            )
                    , LocalTime.now()
            ),
               new TestPerson(
                    6
                    , "Person6"
                    , new String[]{"Test1", "Test2"}
                    , List.of("Test1", "Test2")
                    , LocalDateTime.of(
                    2005, 4, 7, 7, 15
            )
                    , LocalTime.now()
            )
            );

    public static void main(String[] args) {
        TestFileManager instance = new TestFileManager();

        //instance.testCSVRead();
        //instance.testCSVWrite();
        instance.testJSONRead();
        //instance.testJSONWrite();
    }

    
    public void testCSVRead() {
        System.out.println(Arrays.toString(
                csvFileManager.read(
                        new File("/Users/albin/IdeaProjects/ICTSkills/test.csv")
                ).toArray()
        ));
    }

    
    public void testCSVWrite() {
        csvFileManager.write(TEST_PERSONS,"test.csv");
    }

    
    public void testJSONRead() {
        List<TestPerson> testPersons = jsonFileManager.read(new File("/Users/albin/IdeaProjects/ICTSkills/test.json"));
        System.out.println(testPersons.get(0).getFastestLap());
    }

    public void testJSONWrite() {
        jsonFileManager.write(TEST_PERSONS,"test.json");
    }
}
