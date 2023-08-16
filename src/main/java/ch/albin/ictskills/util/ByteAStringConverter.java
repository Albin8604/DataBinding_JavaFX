package ch.albin.ictskills.util;

import ch.albin.ictskills.assets.Assets;
import javafx.util.StringConverter;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ByteAStringConverter extends StringConverter<Byte[]> {
    @Override
    public String toString(Byte[] bytes) {
        return null;
    }

    @Override
    public Byte[] fromString(String s) {
        byte[] bytes;

        System.out.println(s);

        if (s == null){
            return null;
        }

        try {
            bytes = Files.readAllBytes(Path.of(s));
        } catch (IOException e) {
            try {
                bytes = Files.readAllBytes(Path.of(Assets.UnknownPB.asUrl().getPath()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }

        return ArrayUtils.toObject(bytes);
    }
}
