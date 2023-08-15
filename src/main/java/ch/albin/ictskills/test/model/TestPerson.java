package ch.albin.ictskills.test.model;

import ch.albin.ictskills.util.adapter.deserializer.LocalDateTimeDeserializer;
import ch.albin.ictskills.util.adapter.deserializer.LocalTimeDeserializer;
import ch.albin.ictskills.util.adapter.serializer.LocalDateTimeSerializer;
import ch.albin.ictskills.util.adapter.serializer.LocalTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
public class TestPerson {

    @CsvBindByName(column = "ID")
    private int id;

    @CsvBindByName(column = "NAME")
    private String name;

    @CsvBindByName(column = "SIBLINGS")
    private String[] siblings;

    @CsvBindByName(column = "PARENTS")
    private List<String> parents;

    @CsvBindByName(column = "BIRTHDATE")
    @CsvDate("dd.MM.yyyy hh:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime birthdate;

    @CsvBindByName(column = "FASTESTLAP")
    @CsvDate("hh:mm:ss")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime fastestLap;

    public TestPerson() {
    }

    public TestPerson(int id, String name, String[] siblings, List<String> parents, LocalDateTime birthdate, LocalTime fastestLap) {
        this.id = id;
        this.name = name;
        this.siblings = siblings;
        this.parents = parents;
        this.birthdate = birthdate;
        this.fastestLap = fastestLap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getSiblings() {
        return siblings;
    }

    public void setSiblings(String[] siblings) {
        this.siblings = siblings;
    }

    public List<String> getParents() {
        return parents;
    }

    public void setParents(List<String> parents) {
        this.parents = parents;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public LocalTime getFastestLap() {
        return fastestLap;
    }

    public void setFastestLap(LocalTime fastestLap) {
        this.fastestLap = fastestLap;
    }

    @Override
    public String toString() {
        return "TestPerson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", siblings=" + Arrays.toString(siblings) +
                ", parents=" + Arrays.toString(parents.toArray()) +
                ", birthdate=" + birthdate +
                ", fastestLap=" + fastestLap +
                '}';
    }
}
