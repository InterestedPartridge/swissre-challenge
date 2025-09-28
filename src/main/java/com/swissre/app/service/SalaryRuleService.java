package com.swissre.app.service;

import com.swissre.app.model.Employee;
import java.util.*;
import java.util.stream.Collectors;

public final class SalaryRuleService {
    private static final double MIN_FACTOR = 1.2;
    private static final double MAX_FACTOR = 1.5;

    public static final class Violation {
        public final Employee manager;
        public final double actualAvg;
        public final double minExpected;
        public final double maxExpected;
        public final boolean underpaid;
        Violation(Employee m, double avg, double min, double max, boolean under) {
            this.manager = m; this.actualAvg = avg;
            this.minExpected = min; this.maxExpected = max; this.underpaid = under;
        }
        public String pretty() {
            return String.format("%s | avg sub %.0f | should be %.0f-%.0f | %s by %.0f",
                    manager, actualAvg, minExpected, maxExpected,
                    underpaid ? "UNDERPAID" : "OVERPAID",
                    underpaid ? minExpected - manager.getSalary() : manager.getSalary() - maxExpected);
        }
    }

    public static List<Violation> checkAll(Map<Integer, Employee> employees) {
        return employees.values().stream()
                .filter(e -> !e.getDirectReports().isEmpty())
                .map(SalaryRuleService::check)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private static Optional<Violation> check(Employee m) {
        double avg = m.getDirectReports().stream()
                .mapToDouble(Employee::getSalary)
                .average().orElseThrow();
        double min = avg * MIN_FACTOR;
        double max = avg * MAX_FACTOR;
        double s = m.getSalary();
        if (s < min) return Optional.of(new Violation(m, avg, min, max, true));
        if (s > max) return Optional.of(new Violation(m, avg, min, max, false));
        return Optional.empty();
    }
}