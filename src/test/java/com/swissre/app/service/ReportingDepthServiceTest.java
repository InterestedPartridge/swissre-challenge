package com.swissre.app.service;

import com.swissre.app.model.Employee;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ReportingDepthServiceTest {
    @Test
    void chainOf5() {
        Map<Integer, Employee> map = new HashMap<>();
        Employee prev = new Employee(1, "L1", "", 0, null);
        map.put(1, prev);
        for (int i = 2; i <= 6; i++) {
            Employee e = new Employee(i, "L" + i, "", 0, i - 1);
            map.put(i, e);
            map.get(i - 1).addDirectReport(e);
        }
        var deep = ReportingDepthService.findAll(map);
        assertEquals(1, deep.size()); // levels 5 and 6
        assertTrue(deep.stream().anyMatch(d -> d.employee.getId() == 6));
    }
}