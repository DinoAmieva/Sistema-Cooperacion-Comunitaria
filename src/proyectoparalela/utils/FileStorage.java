package proyectoparalela.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    private static final String DATA_DIR = "data";

    public static synchronized <T> List<T> readList(String fileName) {
        ensureDataDir();
        File file = Path.of(DATA_DIR, fileName).toFile();
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            Object obj = ois.readObject();
            @SuppressWarnings("unchecked") List<T> list = (List<T>) obj;
            return list == null ? new ArrayList<>() : list;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static synchronized <T> void writeList(String fileName, List<T> list) {
        ensureDataDir();
        File file = Path.of(DATA_DIR, fileName).toFile();
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            oos.writeObject(list);
        } catch (IOException ignored) { }
    }

    private static void ensureDataDir() {
        try { Files.createDirectories(Path.of(DATA_DIR)); } catch (IOException ignored) { }
    }
}









