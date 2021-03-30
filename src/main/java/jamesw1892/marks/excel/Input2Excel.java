package jamesw1892.marks.excel;

import java.io.IOException;
import java.util.Scanner;

import jamesw1892.marks.cli.Input;
import jamesw1892.marks.core.Course;
import jamesw1892.marks.core.Grade;
import jamesw1892.marks.core.GradeBoundary;

public class Input2Excel {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        Course course = Input.inputCourse(scanner, true);

        System.out.print(System.lineSeparator() + "Filename to write the course to (without .xlsx): ");
        String filename = scanner.nextLine() + ".xlsx";

        GradeBoundary uniGradeBoundary = new GradeBoundary(
            new Grade[] {
                new Grade("3rd", 0.4f),
                new Grade("2.2", 0.5f),
                new Grade("2.1", 0.6f),
                new Grade("1st", 0.7f)
            }
        );

        scanner.close();

        Export.main(course, filename, uniGradeBoundary);
    }
}
