package com.swissre.app.service;

import com.swissre.app.model.Employee;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SalaryRuleServiceTest {
    @Test
    void underpaidManager() {
        Employee ceo = new Employee(1, "C", "E", 100_000, null);
        Employee sub = new Employee(2, "S", "U", 100_000, 1);
        ceo.addDirectReport(sub);
        var v = SalaryRuleService.checkAll(Map.of(1, ceo, 2, sub));
        assertEquals(1, v.size());
        assertTrue(v.get(0).underpaid);
    }
    @Test
    void overpaidManager() {
        Employee ceo = new Employee(1, "C", "E", 200_000, null);
        Employee sub = new Employee(2, "S", "U", 100_000, 1);
        ceo.addDirectReport(sub);
        var v = SalaryRuleService.checkAll(Map.of(1, ceo, 2, sub));
        assertEquals(1, v.size());
        assertFalse(v.get(0).underpaid);
    }
    @Test
    void okManager() {
        Employee ceo = new Employee(1, "C", "E", 130_000, null);
        Employee sub = new Employee(2, "S", "U", 100_000, 1);
        ceo.addDirectReport(sub);
        var v = SalaryRuleService.checkAll(Map.of(1, ceo, 2, sub));
        assertTrue(v.isEmpty());
    }
}