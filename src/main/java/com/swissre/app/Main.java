package com.swissre.app;

import com.swissre.app.model.Employee;
import com.swissre.app.repo.EmployeeCsvLoader;
import com.swissre.app.service.ReportingDepthService;
import com.swissre.app.service.SalaryRuleService;

import java.nio.file.*;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: java -jar app.jar employees.csv");
            System.exit(1);
        }
        Map<Integer, Employee> map = EmployeeCsvLoader.load(Paths.get(args[0]));

        System.out.println("=== Managers violating salary rule ===");
        SalaryRuleService.checkAll(map).forEach(v -> System.out.println(v.pretty()));

        System.out.println("\n=== Employees with too long reporting line ===");
        ReportingDepthService.findAll(map).forEach(d -> System.out.println(d.pretty()));
    }
}