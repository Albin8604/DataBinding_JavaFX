package ch.albin.ictskills.util;

import javafx.util.StringConverter;

public class DoubleIntegerStringConverter extends StringConverter<Number> {
    public DoubleIntegerStringConverter() {
    }

    @Override
    public String toString(Number object) {
        if(object.intValue()!=object.doubleValue())
            return "";
        return ""+(object.intValue());
    }

    @Override
    public Number fromString(String string) {
        Number val = Double.parseDouble(string);
        return val.intValue();
    }
}
