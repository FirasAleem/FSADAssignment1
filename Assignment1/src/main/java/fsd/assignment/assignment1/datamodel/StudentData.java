package fsd.assignment.assignment1.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class StudentData {
    /*  include an instance of StudentData
    */
    private static StudentData instance = new StudentData();
    private static String filename = "student-data.txt";

    private ObservableList<Student> students;

    public static StudentData getInstance() {
        /* complete the getter for the instance created
        */
        return instance;
    }

    public ObservableList<Student> getStudents() {
        /* complete the getter for the observable arraylist
        */
        return students;
    }

    public void loadStudentData() throws IOException {
        students = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try {
            while ((input = br.readLine()) != null) {
                /*  split each input line using a tab
                 */
                String[] studentItem = input.split("\t");
                /* using the String create each piece of data so that all the instance variables
                         have a value accordingly
                 */
                String studId = studentItem[0];
                String yearStudy = studentItem[1];
                String mod1 = studentItem[2];
                String mod2 = studentItem[3];
                String mod3 = studentItem[4];
                /*  complete the call to the constructor by passing in the parameters
                 */
                Student studDataItem = new Student(studId, yearStudy, mod1, mod2, mod3);
                /* add the studentDataItem to the students array
                 */
                //>include the statement here
                students.add(studDataItem);
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public void storeStudentData() throws IOException {
        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);
        try {
            /* complete the iterator*/
            Iterator<Student> it = students.iterator();
            while (it.hasNext()) {
                /* accept the next item from the iterated list*/
                Student item = it.next();
                /*  complete write() using String.format remember to separate each string with a tab*/
                bw.write(String.format("%s\t%s\t%s\t%s\t%s",
                        item.getStudId(),
                        item.getYearOfStudy(),
                        item.getModule1(),
                        item.getModule2(),
                        item.getModule3()));
                /* once a student item is written to the file ensure that the next item is stored on a new line*/
                bw.newLine();
            }
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    public void addStudentData(Student studentToAdd){
        /* complete the addStudentData so that a student can be added
                 to students
         */
        students.add(studentToAdd);
    }
    public void deleteStudent(Student stu){
        /* complete the addStudentData so that a student can be removed
                 from students
         */
        students.remove(stu);
    }
}
