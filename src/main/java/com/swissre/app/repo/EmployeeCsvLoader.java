package com.swissre.app.repo;

import com.swissre.app.model.Employee;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

public final class EmployeeCsvLoader {
    public static Map<Integer, Employee> load(Path file) throws IOException {
        Map<Integer, Employee> map = new HashMap<>();
        try (Stream<String> lines = Files.lines(file)) {
            lines.skip(1)// skip heading row
                    .filter(l -> !l.trim().isEmpty())
                    .forEach(l -> {
                        String[] p = l.split(",", -1);
                        int id = (int) Double.parseDouble(p[0]);
                        double salary = Double.parseDouble(p[3]);
                        Integer mgr = p[4].isEmpty() ? null : (int) Double.parseDouble(p[4]);
                        map.put(id, new Employee(id, p[1], p[2], salary, mgr));
                    });
        }
        // wire manager → directReport
        map.values().forEach(e ->
                e.getManagerId().ifPresent(mgrId ->
                        map.get(mgrId).addDirectReport(e)));
        return map;
    }
}