package jamesw1892.marks.cli;

import java.util.Scanner;

import jamesw1892.marks.core.Course;
import jamesw1892.marks.core.Year;
import jamesw1892.marks.core.Module;
import jamesw1892.marks.core.Assessment;

public class Input {

    private static Assessment inputAssessment(Scanner scanner, int assessmentNum, String moduleName, boolean fast) {

        System.out.println(String.format("%sASSESSMENT %d in year %s:", System.lineSeparator(), assessmentNum, moduleName));

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Weight relative to module (between 0 and 1): ");
        float weightOfModule = scanner.nextFloat();
        scanner.nextLine();

        String notes = "";
        Float mark = null;

        if (!fast) {
            System.out.println("Notes:");
            notes = scanner.nextLine();

            System.out.print("Do you already know the mark? ");
            if (scanner.nextBoolean()) {
                scanner.nextLine();
                System.out.print("Mark (between 0 and 1): ");
                mark = scanner.nextFloat();
            }
            scanner.nextLine();
        }

        return new Assessment(name, weightOfModule, notes, mark);
    }

    private static Module inputModule(Scanner scanner, int moduleNum, int yearNum, boolean fast) {

        System.out.println(String.format("%sMODULE %d in year %d:", System.lineSeparator(), moduleNum, yearNum));

        System.out.print("Module Name: ");
        String name = scanner.nextLine();

        System.out.print("CATS: ");
        int CATS = scanner.nextInt();
        scanner.nextLine();

        String[] lecturers = new String[0];
        String description = "";

        if (!fast) {
            System.out.print("Lecturers (comma separated): ");
            String lecturersStr = scanner.nextLine();
            lecturers = lecturersStr.split(",");

            System.out.print("Description: ");
            description = scanner.nextLine();
        }

        System.out.print("How many assessments are there in this module? ");
        int numAssessments = scanner.nextInt();
        scanner.nextLine();

        Assessment[] assessments = new Assessment[numAssessments];
        for (int assessmentNum = 0; assessmentNum < numAssessments; assessmentNum++) {
            assessments[assessmentNum] = inputAssessment(scanner, assessmentNum + 1, name, fast);
        }

        return new Module(name, CATS, lecturers, description, assessments);
    }

    private static Year inputYear(Scanner scanner, int yearNum, boolean fast) {

        System.out.println(System.lineSeparator() + "YEAR " + String.valueOf(yearNum) + ":");

        String personalTutor = "";
        if (!fast) {
            System.out.print("Personal Tutor: ");
            personalTutor = scanner.nextLine();
        }

        System.out.print("Weight (proportion of the course this year is worth, between 0 and 1): ");
        float weight = scanner.nextFloat();
        scanner.nextLine();

        System.out.print("How many modules are you taking this year? ");
        int numModules = scanner.nextInt();
        scanner.nextLine();

        Module[] modules = new Module[numModules];
        for (int moduleNum = 0; moduleNum < numModules; moduleNum++) {
            modules[moduleNum] = inputModule(scanner, moduleNum + 1, yearNum, fast);
        }

        return new Year(yearNum, personalTutor, weight, modules);
    }

    /**
     * 
     * @param scanner
     * @param fast      Whether to only input the necessary information and leave out
     * descriptions, notes and marks
     * @return
     */
    public static Course inputCourse(Scanner scanner, boolean fast) {
        String name = "";
        String description = "";

        if (!fast) {
            System.out.print("Course name: ");
            name = scanner.nextLine();

            System.out.println("Course description:");
            if (!fast) description = scanner.nextLine();
        }

        System.out.print("How many years does the course last? ");
        int numYears = scanner.nextInt();
        scanner.nextLine();

        Year[] years = new Year[numYears];
        for (int yearNum = 0; yearNum < numYears; yearNum++) {
            years[yearNum] = inputYear(scanner, yearNum + 1, fast);
        }

        return new Course(name, description, years);
    }

    private static void viewCourse(Course course) {
        System.out.println("Average mark: " + course.getMarkAverageStr());
    }

    private static void viewAssessment(Assessment assessment) {
        System.out.println("Average mark: " + assessment.getMarkStr());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Course course = inputCourse(scanner);
        Assessment assessment = new Assessment("", 1f, "", 1f); //inputAssessment(scanner);

        // choose what want to do
        System.out.println("What do you want to do?\n1) View assessment");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                viewAssessment(assessment);
                break;
            default:
                System.out.println("Invalid option");
        }

        scanner.close();
    }
}
