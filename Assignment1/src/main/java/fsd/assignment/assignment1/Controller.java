package fsd.assignment.assignment1;

import fsd.assignment.assignment1.datamodel.Student;
import fsd.assignment.assignment1.datamodel.StudentData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Controller {

    //these variables correspond to the <top> of main-view.fxml
    @FXML
    private TextField studId;

    @FXML
    private TextField yearStudy;

    @FXML
    private ChoiceBox<String> mod1Choice;

    @FXML
    private ChoiceBox<String> mod2Choice;

    @FXML
    private ChoiceBox<String> mod3Choice;

    private String choice1, choice2, choice3;

    private String[] modChoices = {"OOP", "Data Algo", "DS", "Maths", "AI",
            "Adv Programming", "Project"};

    @FXML
    private Label validateStudent; //remember this is the Label that you only see when there is an invalid "add"

    //validateStudent is the last element corresponding to <top>

    //these variables correspond to the <left> i.e. the studentListView
    @FXML
    private ListView<Student> studentListView;

    //these variables correspond to the <bottom> part of the border
    @FXML
    private Label yearStudyView;

    @FXML
    private Label mod1View;

    @FXML
    private Label mod2View;

    @FXML
    private Label mod3View;

    //mod3View is the last element for the bottom part of the border

    //the contextMenu is used for the right-click regarding Edit / Delete
    @FXML
    private ContextMenu listContextMenu;

    //this variable is used when switching windows from add to edit
    @FXML
    private BorderPane mainWindow;

    //used to add a student to the ArrayList for addStudentData()
    public Student studentToAdd;


    public void initialize() {

        studentListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                /*  complete the code so that if a studId is selected then the changed()
                         should ensure that the other fields related to the selected item appear at the bottom of the window
                */
                if (newValue != null) {
                    Student student = studentListView.getSelectionModel().getSelectedItem();
                    yearStudyView.setText(student.getYearOfStudy());
                    mod1View.setText(student.getModule1());
                    mod2View.setText(student.getModule2());
                    mod3View.setText(student.getModule3());

                }
            }
        });

        //the setOnAction ensures that when a ChoiceBox is selected the getChoice() grabs the selected choice
        mod1Choice.setOnAction(this::getChoice);
        mod2Choice.setOnAction(this::getChoice);
        mod3Choice.setOnAction(this::getChoice);

        /*      the array declared above for modChoices must be added to each Choice box
                 include the code here to addAll()
        */
        //insert the code to addAll() the modChoices [] to each ChoiceBox here
        mod1Choice.getItems().addAll(modChoices);
        mod2Choice.getItems().addAll(modChoices);
        mod3Choice.getItems().addAll(modChoices);


        //deleting a student
        /*  create a new listContextMenu -> defined above in the variables
        */
        listContextMenu = new ContextMenu();
        /*  create a MenuItem object so that when the user right-clicks a studId
                 the word Delete? appears
         */
        MenuItem deleteStudent = new MenuItem("Delete");

        deleteStudent.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                /*  get the item to be deleted and call the deleteStudent()
                 */
                Student student = studentListView.getSelectionModel().getSelectedItem();
                deleteStudent(student);
            }
        });

        //editing a student
        /* create a new listContextMenu -> defined above in the variables
         */

        /* create a MenuItem object so that when the user right-clicks a studId
                 the word Edit?? appears
         */
        MenuItem editStudent = new MenuItem("Edit");

        editStudent.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                /* get the item to be edited and call the editStudent()
                 */
                Student student = studentListView.getSelectionModel().getSelectedItem();
                editStudent(student);
            }
        });

        //code provided to ensure that contextMenu appears as part of the above actions
        listContextMenu.getItems().add(deleteStudent);
        listContextMenu.getItems().add(editStudent);

        //to ensure access to a particular cell in the studentListView
        studentListView.setCellFactory(new Callback<>() {
            public ListCell<Student> call(ListView<Student> param) {
                ListCell<Student> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(Student stu, boolean empty) {
                        /* TODO: ?? ensure that the studentListView has studId's or not when
                                 the delete a student takes place
                         */
                        super.updateItem(stu, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(stu.getStudId());
                        }
                    }//end of update()
                };
                //code included as part of delete
                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty) -> {
                            if (isNowEmpty) {
                                cell.setContextMenu(null);
                            } else {
                                cell.setContextMenu(listContextMenu);
                            }
                        });
                return cell;
            }
        }); //end of setting the cell factory

        /*  ensure that the studId's are sorted according to year of study in ascending order
        */

        SortedList<Student> sortedByYear = new SortedList<>(StudentData.getInstance().getStudents(),
                new Comparator<>() {
                    @Override
                    public int compare(Student o1, Student o2) {
                        return o1.getYearOfStudy().compareTo(o2.getYearOfStudy());
                    }
                });

        /*  step 1 - set items using the sorted list
                 step 2 - ensure that only one studId can be selected at one time
                 step 3 - ensure that the first studId is highlighted when the application commences
         */
        studentListView.setItems(sortedByYear);
        studentListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        studentListView.getSelectionModel().selectFirst();
    }

    public void getChoice(ActionEvent event) {
        /* TODO: ??? make use of event to determine each choice and assign each module choice to
                 choice1, choice2 and choice3
         */
        choice1 = mod1Choice.getValue();
        choice2 = mod2Choice.getValue();
        choice3 = mod3Choice.getValue();
    }


    @FXML
    public void addStudentData() {
        /*  get the values from the text fields
         */
        String studIdS = studId.getText();
        String yearStudyS = yearStudy.getText();
        /*  validate whether the studIdS and yearStudyS are occupied, BOTH have to be occupied
                 for the add to take place, if one or both are unoccupied print the following message
                 in the validateStudent label -> message to be printed is
                 "Error: cannot add student if studId or year of study not filled in"
                 If both are occupied then go ahead with the addStudentData()
         */
        if (studIdS.isEmpty() || yearStudyS.isEmpty()){
            validateStudent.setText("Error: cannot add student if studId or year of study not filled in");
        }else {
            //do the else...here, first ensure that the validateStudent label is clear of any text
            //use the getInstance() to addStudentData()
            //select the student that has been added so that it is highlighted on the list
            validateStudent.setText(null);
            studentToAdd = new Student(studIdS, yearStudyS, choice1, choice2, choice3);
            StudentData.getInstance().addStudentData(studentToAdd);
            studentListView.getSelectionModel().select(studentToAdd);
        }
    }

    public void deleteStudent(Student stu) {
        /* create an alert object to confirm that a user wants to delete*/
        Alert alert = new Alert((Alert.AlertType.CONFIRMATION));
        /* set the title with "Delete a student from the list"*/
        alert.setTitle("Delete a student from the list");
        /* set the header text with "Deleting student " xxx - where xxx is the studId*/
        alert.setHeaderText("Deleting student " + stu.getStudId());
        /* have a message that asks the user "Are you sure you want to delete the student?"*/
        alert.setContentText("Are you sure you want to delete the student?");
        //the result object with the showAndWait() has been completed for you
        Optional<ButtonType> result = alert.showAndWait();
        /* include a test to verify if the result is present and whether the OK button was pressed,
            if so go ahead and call the deleteStudent()*/
        if (result.isPresent() && result.get() == ButtonType.OK){
            StudentData.getInstance().deleteStudent(stu);
        }
    }

    public void editStudent(Student stu) {
        //the dialog object has been completed for you
        Dialog<ButtonType> dialog = new Dialog<>();
        /*  use the dialog object to set the owner, the title and the header text
                 the title must state "Edit a student's details"
                 the header text must state: Editing student ID: xxx - where xxx is the studId*/
        dialog.initOwner(mainWindow.getScene().getWindow());
        dialog.setTitle("Edit a student's details");
        dialog.setHeaderText("Editing student Id: " + stu.getStudId());
        /* complete the FXMLLoader statement*/
        FXMLLoader fxmlLoader = new FXMLLoader();
        /* use the fxmlLoader to set the edit-students.fxml*/
        fxmlLoader.setLocation(getClass().getResource("edit-students.fxml"));
        try {
            /* load the fxml*/
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException event) {
            /* print an appropriate message if it cannot be loaded & print stacktrace*/
            System.out.println("Could not load the dialog");
            event.printStackTrace();
            return;
        }
        /* complete the ec controller statement*/
        EditStudentController ec = fxmlLoader.getController();
        /* use the ec object to call setToEdit()*/
        ec.setToEdit(stu);
        /* create the OK and CANCEL buttons using dialog*/
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        //the result object with the showAndWait() has been completed for you
        Optional<ButtonType> result = dialog.showAndWait();

        /* verify if there is an edit to complete, complete the editStudent call processEdit()
                  ensure that the student edited is selected*/
        //remove the comments and complete the if...
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Student editStudent = ec.processEdit(stu);
            //select the edited studId here
            studentListView.getSelectionModel().select(editStudent);
        }
    }
}