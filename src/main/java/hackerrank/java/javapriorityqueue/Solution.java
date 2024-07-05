package hackerrank.java.javapriorityqueue;

import java.util.*;

import java.util.stream.Collectors;
/*
 * Create the Student and Priorities classes here.
 */

class Student {
    private final int id;
    private final String name;
    private final double cgpa;

    public Student(int id, String name, double cgpa) {
        this.id = id;
        this.name = name;
        this.cgpa = cgpa;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCGPA() {
        return cgpa;
    }
}

class Priorities {
    // private final List<Student> studentList;

    public List<Student> getStudents(List<String> events) {
        // List<Student> studentList = new ArrayList<>();
        PriorityQueue<Student> studentsQueue = new PriorityQueue<>();

        // for ()

        Comparator<Student> alphabeticalComparator = new Comparator<Student>() {

            @Override
            public int compare(Student o1, Student o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };

        return studentsQueue.stream().sorted(alphabeticalComparator).collect(Collectors.toList());
    }
}


public class Solution {
    private final static Scanner scan = new Scanner(System.in);
    private final static Priorities priorities = new Priorities();

    public static void main(String[] args) {
        int totalEvents = Integer.parseInt(scan.nextLine());
        List<String> events = new ArrayList<>();

        while (totalEvents-- != 0) {
            String event = scan.nextLine();
            events.add(event);
        }

        List<Student> students = priorities.getStudents(events);

        if (students.isEmpty()) {
            System.out.println("EMPTY");
        } else {
            for (Student st: students) {
                System.out.println(st.getName());
            }
        }
    }
}