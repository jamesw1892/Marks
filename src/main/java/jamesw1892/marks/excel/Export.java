package jamesw1892.marks.excel;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jamesw1892.marks.core.Assessment;
import jamesw1892.marks.core.Course;
import jamesw1892.marks.core.Grade;
import jamesw1892.marks.core.GradeBoundary;
import jamesw1892.marks.core.Year;
import jamesw1892.marks.core.Module;

public class Export {

    private static void createSheetCourse(Course course, XSSFWorkbook workbook, GradeBoundary gradeBoundary) {
        XSSFSheet sheet = workbook.createSheet("Course");

        XSSFRow row0 = sheet.createRow((short) 0);
        row0.createCell(0).setCellValue("Name");
        row0.createCell(1).setCellValue(course.getName());

        XSSFRow row1 = sheet.createRow((short) 1);
        row1.createCell(0).setCellValue("Description");
        row1.createCell(1).setCellValue(course.getDescription());

        XSSFRow row2 = sheet.createRow((short) 2);
        row2.createCell(0).setCellValue("Complete");
        XSSFCell cellComplete = row2.createCell(1);
        // sum of weight so far (column I in years)
        cellComplete.setCellFormula("SUM(Years!I:I)");

        XSSFRow row3 = sheet.createRow((short) 3);
        row3.createCell(0).setCellValue("Mark");
        XSSFCell cellMark = row3.createCell(1);
        cellMark.setCellFormula(String.format("IF(%s = 0, \"\", SUM(Years!J:J) / %s)", addr(cellComplete), addr(cellComplete)));

        XSSFRow row4 = sheet.createRow((short) 4);
        row4.createCell(0).setCellValue("Grade");
        row4.createCell(1).setCellFormula(getGradeFormula(cellMark));
    }

    private static void createSheetYears(Course course, XSSFWorkbook workbook) {
        XSSFSheet sheetYears = workbook.createSheet("Years");

        XSSFRow heading = sheetYears.createRow((short) 0);
        heading.createCell(0).setCellValue("Year");
        heading.createCell(1).setCellValue("CATS");
        heading.createCell(2).setCellValue("Weight");
        heading.createCell(3).setCellValue("Personal Tutor");
        heading.createCell(4).setCellValue("Complete");
        heading.createCell(5).setCellValue("Mark");
        heading.createCell(6).setCellValue("Grade");
        heading.createCell(7).setCellValue("Mark Current");
        heading.createCell(8).setCellValue("Mark So Far");
        heading.createCell(9).setCellValue("Weighted Mark");

        short i = 1;
        for (Year year: course.getYears()) {
            XSSFRow row = sheetYears.createRow(i);

            XSSFCell cellYear = row.createCell(0);
            cellYear.setCellValue(year.getNumber());

            row.createCell(1).setCellValue(year.getCATS());
            
            XSSFCell cellWeight = row.createCell(2);
            cellWeight.setCellValue(year.getWeightOfCoursePercent());

            row.createCell(3).setCellValue(year.getPersonalTutor());

            // sum of weight so far (column K in modules) where the year is this one
            XSSFCell cellComplete = row.createCell(4);
            cellComplete.setCellFormula(String.format("SUMIF(Modules!A:A, %s, Modules!K:K)", addr(cellYear)));

            XSSFCell cellMark = row.createCell(5);

            row.createCell(6).setCellFormula(getGradeFormula(cellMark));

            // sum of weighted marks (column L in modules) where the year is this one
            XSSFCell cellMarkCurrent = row.createCell(7);
            cellMarkCurrent.setCellFormula(String.format("SUMIF(Modules!A:A, %s, Modules!L:L)", addr(cellYear)));

            row.createCell(8).setCellFormula(String.format("%s * %s", addr(cellComplete), addr(cellWeight)));
            row.createCell(9).setCellFormula(String.format("%s * %s", addr(cellMarkCurrent), addr(cellWeight)));

            cellMark.setCellFormula(String.format("IF(%s = 0, \"\", %s / %s)", addr(cellComplete), addr(cellMarkCurrent), addr(cellComplete)));

            i++;
        }
    }

    private static void createSheetModules(Course course, XSSFWorkbook workbook) {
        XSSFSheet sheetModules = workbook.createSheet("Modules");

        XSSFRow heading = sheetModules.createRow((short) 0);
        heading.createCell(0).setCellValue("Year");
        heading.createCell(1).setCellValue("Name");
        heading.createCell(2).setCellValue("CATS");
        heading.createCell(3).setCellValue("Weight");
        heading.createCell(4).setCellValue("Complete");
        heading.createCell(5).setCellValue("Mark");
        heading.createCell(6).setCellValue("Grade");
        heading.createCell(7).setCellValue("Lecturers");
        heading.createCell(8).setCellValue("Description");
        heading.createCell(9).setCellValue("Mark Current");
        heading.createCell(10).setCellValue("Weight done so far");
        heading.createCell(11).setCellValue("Weighted Mark");

        short i = 1;
        for (Year year: course.getYears()) {
            for (Module module: year.getModules()) {
                XSSFRow row = sheetModules.createRow(i);

                row.createCell(0).setCellValue(year.getNumber());

                XSSFCell cellName = row.createCell(1);
                cellName.setCellValue(module.getName());

                XSSFCell cellCATS = row.createCell(2);
                cellCATS.setCellValue(module.getCATS());

                XSSFCell cellWeight = row.createCell(3);
                cellWeight.setCellFormula(String.format("%s / %d", addr(cellCATS), year.getCATS()));

                // sum of weight so far (column I in assessments) where the module is this one
                XSSFCell cellComplete = row.createCell(4);
                cellComplete.setCellFormula(String.format("SUMIF(Assessments!B:B, %s, Assessments!I:I)", addr(cellName)));

                XSSFCell cellMark = row.createCell(5);

                row.createCell(6).setCellFormula(getGradeFormula(cellMark));
                row.createCell(7).setCellValue(String.join(", ", module.getLecturers()));
                row.createCell(8).setCellValue(module.getDescription());

                // sum of weighted marks (column H in assessments) where the module is this one
                XSSFCell cellMarkCurrent = row.createCell(9);
                cellMarkCurrent.setCellFormula(String.format("SUMIF(Assessments!B:B, %s, Assessments!H:H)", addr(cellName)));

                row.createCell(10).setCellFormula(String.format("%s * %s", addr(cellComplete), addr(cellWeight)));
                row.createCell(11).setCellFormula(String.format("%s * %s", addr(cellMarkCurrent), addr(cellWeight)));

                cellMark.setCellFormula(String.format("IF(%s = 0, \"\", %s / %s)", addr(cellComplete), addr(cellMarkCurrent), addr(cellComplete)));

                i++;
            }
        }
    }

    /**
     * Return the formula to calculate the grade of the mark in the given cell
     * 
     * Should work with any order of grade boundaries in the grade boundaries tab
     */
    private static String getGradeFormula(XSSFCell cell) {
        String gb = "'Grade Boundaries'!$";
        String address = addr(cell);
        return String.format("IF(%s = \"\", \"\", XLOOKUP(MAXIFS(%sB:B, %sB:B, \"<=\" & %s), %sB:B, %sA:A))", address, gb, gb, address, gb, gb);
    }

    /**
     * Get the address of the given cell in the form "A1"
     */
    private static String addr(XSSFCell cell) {
        return cell.getAddress().formatAsString();
    }

    private static void createSheetAssessments(Course course, XSSFWorkbook workbook) {
        XSSFSheet sheetAssessments = workbook.createSheet("Assessments");

        XSSFRow heading = sheetAssessments.createRow((short) 0);
        heading.createCell(0).setCellValue("Year");
        heading.createCell(1).setCellValue("Module");
        heading.createCell(2).setCellValue("Name");
        heading.createCell(3).setCellValue("Weight");
        heading.createCell(4).setCellValue("Mark");
        heading.createCell(5).setCellValue("Grade");
        heading.createCell(6).setCellValue("Notes");
        heading.createCell(7).setCellValue("Weighted Mark");
        heading.createCell(8).setCellValue("Weight done so far");

        short i = 1;
        for (Year year: course.getYears()) {
            for (Module module: year.getModules()) {
                for (Assessment assessment: module.getAssessments()) {
                    XSSFRow row = sheetAssessments.createRow(i);

                    row.createCell(0).setCellValue(year.getNumber());
                    row.createCell(1).setCellValue(module.getName());
                    row.createCell(2).setCellValue(assessment.getName());
                    XSSFCell weightCell = row.createCell(3);
                    weightCell.setCellValue(assessment.getWeightOfModule());
                    XSSFCell markCell = row.createCell(4);
                    if (assessment.getMark() != null) {
                        markCell.setCellValue(assessment.getMark());
                    }
                    row.createCell(5).setCellFormula(getGradeFormula(markCell));
                    row.createCell(6).setCellValue(assessment.getNotes());
                    row.createCell(7).setCellFormula(addr(markCell) + "*" + addr(weightCell));
                    row.createCell(8).setCellFormula("IF(" + addr(markCell) + "=\"\",0," + addr(weightCell) + ")");

                    i++;
                }
            }
        }
    }

    private static void createSheetGradeBoundaries(GradeBoundary gradeBoundary, XSSFWorkbook workbook) {
        XSSFSheet sheetGradeBoundaries = workbook.createSheet("Grade Boundaries");

        XSSFRow heading = sheetGradeBoundaries.createRow((short) 0);
        heading.createCell(0).setCellValue("Grade");
        heading.createCell(1).setCellValue("Min Mark");

        short i = 1;
        for (Grade grade: gradeBoundary.getGrades()) {
            XSSFRow row = sheetGradeBoundaries.createRow(i);

            row.createCell(0).setCellValue(grade.getName());
            row.createCell(1).setCellValue(grade.getMinimumPercentRequired());

            i++;
        }
    }

    /**
     * Export the given course into an excel file
     * with given name and path
     * @param course
     * @param filename
     */
    public static void main(Course course, String filename, GradeBoundary gradeBoundary) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();

        createSheetCourse(course, workbook, gradeBoundary);
        createSheetYears(course, workbook);
        createSheetModules(course, workbook);
        createSheetAssessments(course, workbook);
        createSheetGradeBoundaries(gradeBoundary, workbook);

        // workbook.evaluateAll();
        // workbook.setForceFormulaRecalculation(true);

        // write to file
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();
    }
}
