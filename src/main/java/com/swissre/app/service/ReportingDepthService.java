package com.swissre.app.service;

import com.swissre.app.model.Employee;

import java.util.*;

public final class ReportingDepthService {
    private static final int MAX_DEPTH = 4;

    public static final class TooDeep {
        public final Employee employee;
        public final int depth;
        TooDeep(Employee e, int d) { this.employee = e; this.depth = d; }
        public String pretty() {
            return String.format("%s has %d managers in chain (limit %d)", employee, depth, MAX_DEPTH);
        }
    }

    public static List<TooDeep> findAll(Map<Integer, Employee> empMap) {
        List<TooDeep> result = new ArrayList<>();
        Map<Integer, Integer> cache = new HashMap<>();
        for (Employee e : empMap.values()) {
            int d = depth(e, empMap, cache);
            if (d > MAX_DEPTH) result.add(new TooDeep(e, d));
        }
        return result;
    }

    private static int depth(Employee e,
                             Map<Integer, Employee> map,
                             Map<Integer, Integer> cache) {
        Integer cached = cache.get(e.getId());
        if (cached != null) return cached;

        int val = e.getManagerId()
                .map(mid -> 1 + depth(map.get(mid), map, cache))
                .orElse(0);
        cache.put(e.getId(), val);
        return val;
    }
}