package jamesw1892.marks.cli;

import java.util.Scanner;

import jamesw1892.marks.core.Course;
import jamesw1892.marks.core.Year;
import jamesw1892.marks.core.Module;
import jamesw1892.marks.core.Assessment;

public class Input {

    private static Year inputYear(Scanner scanner, int yearNum) {
        return new Year(yearNum, "", 0.0f, new Module[0]);
    }

    private static Course inputCourse(Scanner scanner) {
        System.out.print("Course name: ");
        String name = scanner.nextLine();

        System.out.println("Course description:");
        String description = scanner.nextLine();

        System.out.print("How many years does the course last? ");
        int numYears = scanner.nextInt();

        Year[] years = new Year[numYears];
        for (int yearNum = 0; yearNum < numYears; yearNum++) {
            years[yearNum] = inputYear(scanner, yearNum + 1);
        }

        return new Course(name, description, years);
    }

    private static void viewCourse(Course course) {
        System.out.println("Average mark: " + course.getMarkAverageStr());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Course course = inputCourse(scanner);

        // choose what want to do
        System.out.println("What do you want to do?\n1) View course");
        switch (scanner.nextInt()) {
            case 1:
                viewCourse(course);
                break;
            default:
                System.out.println("Invalid option");
        }

        scanner.close();
    }
}
