package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

public class CRUD extends Application {
	//instances of Questions and Answers class to hold all objects
    private Questions questions = new Questions(); 
    private Answers answers = new Answers(); 
    
    //JavaFX ListView to display lists
    private ListView<Question> questionListView;
    private ListView<String> answerListView; 
    
    //JavaFX ObservableList to hold and update UI elements
    private ObservableList<Question> questionObservableList;
    private ObservableList<String> answerObservableList;

    //input fields
    private TextField questionInput; 
    private TextField answerInput;  
    
    //buttons
    private Button addQuestionButton;
    private Button addAnswerButton;
    private Button editQuestionButton;
    private Button deleteQuestionButton;
    private Button editAnswerButton;
    private Button deleteAnswerButton;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Welcome to Temu Ed Disscussion");


        //create ObservableList for questions
        questionObservableList = FXCollections.observableArrayList(questions.getAllQuestions());
        questionListView = new ListView<>(questionObservableList);
        questionListView.setPrefHeight(150);

        //create ObservableList for answers
        answerObservableList = FXCollections.observableArrayList();
        answerListView = new ListView<>(answerObservableList);
        answerListView.setPrefHeight(200);
        
        //UI labels
        Label welcomeLabel = new Label("Select a question to view answers:");
        welcomeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label answerLabel = new Label("Answers:");

        //question and answer inputs and button formatting
        questionInput = new TextField();
        questionInput.setPromptText("Enter new question...");
        addQuestionButton = new Button("Add Question");
        editQuestionButton = new Button("Edit Question");
        deleteQuestionButton = new Button("Delete Question");
    
        answerInput = new TextField();
        answerInput.setPromptText("Enter an answer...");
        addAnswerButton = new Button("Add Answer");
        editAnswerButton = new Button("Edit Answer");
        deleteAnswerButton = new Button("Delete Answer");

        //event listener (observable object, prev selection, new selection)
        questionListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateAnswers(newSelection.getId());
            }
        });

        //add a question button
        addQuestionButton.setOnAction(e -> {
            String newQuestionText = questionInput.getText().trim();
            if (!newQuestionText.isEmpty()) {
                questions.addQuestion(newQuestionText);
                refreshQuestionList();
                questionInput.clear();
            } else {
            	//validation - check that question is not empty
                showAlert("Error", "Question cannot be empty");
            }
        });
        //edit question
        editQuestionButton.setOnAction(e -> {
            Question selectedQuestion = questionListView.getSelectionModel().getSelectedItem();
            if (selectedQuestion != null) {
                String updatedText = showInputDialog("Edit Question", "Enter new question text:", selectedQuestion.getText());
                if (updatedText != null && !updatedText.trim().isEmpty()) {
                    questions.editQuestion(selectedQuestion.getId(), updatedText);
                    refreshQuestionList();
                }
            }
        });
        
        //delete question
        deleteQuestionButton.setOnAction(e -> {
            Question selectedQuestion = questionListView.getSelectionModel().getSelectedItem();
            if (selectedQuestion != null) {
                questions.deleteQuestion(selectedQuestion.getId());
                refreshQuestionList();
                answerObservableList.clear();
            }
        });

        //add answer to selected question
        addAnswerButton.setOnAction(e -> {
            Question selectedQuestion = questionListView.getSelectionModel().getSelectedItem();
            if (selectedQuestion != null) {
                String newAnswerText = answerInput.getText().trim();
                if (!newAnswerText.isEmpty()) {
                    answers.addAnswer(selectedQuestion.getId(), newAnswerText);
                    updateAnswers(selectedQuestion.getId());
                    answerInput.clear();
                } else {
                    showAlert("Error", "Answer cannot be empty");
                }
            } else {
            	//validation - check that the answer is assigned to a question
                showAlert("Error", "Must select a question before adding an answer");
            }
        });
        
        editAnswerButton.setOnAction(e -> {
            String selectedAnswerText = answerListView.getSelectionModel().getSelectedItem();
            Question selectedQuestion = questionListView.getSelectionModel().getSelectedItem();
            if (selectedQuestion != null && selectedAnswerText != null) {
                String updatedText = showInputDialog("Edit Answer", "Enter new answer text:", selectedAnswerText);
                if (updatedText != null && !updatedText.trim().isEmpty()) {
                    int answerId = answers.getAnswersForQuestion(selectedQuestion.getId())
                                         .stream()
                                         .filter(a -> a.getText().equals(selectedAnswerText))
                                         .findFirst()
                                         .map(Answer::getId)
                                         .orElse(-1);
                    if (answerId != -1) {
                        answers.editAnswer(answerId, updatedText);
                        updateAnswers(selectedQuestion.getId());
                    }
                }
            }
        });

        //delete answer
        deleteAnswerButton.setOnAction(e -> {
            String selectedAnswerText = answerListView.getSelectionModel().getSelectedItem();
            Question selectedQuestion = questionListView.getSelectionModel().getSelectedItem();
            if (selectedQuestion != null && selectedAnswerText != null) {
                int answerId = answers.getAnswersForQuestion(selectedQuestion.getId())
                                      .stream()
                                      .filter(a -> a.getText().equals(selectedAnswerText))
                                      .findFirst()
                                      .map(Answer::getId)
                                      .orElse(-1);
                if (answerId != -1) {
                    answers.deleteAnswer(answerId);
                    updateAnswers(selectedQuestion.getId());
                }
            }
        });

        //layout for adding questions
        HBox questionBox = new HBox(10, questionInput, addQuestionButton, editQuestionButton, deleteQuestionButton);
        questionBox.setPadding(new Insets(5));

        //layout for adding answers
        HBox answerBox = new HBox(10, answerInput, addAnswerButton, editAnswerButton, deleteAnswerButton);
        answerBox.setPadding(new Insets(5));

        //main layout
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        mainLayout.getChildren().addAll(
                welcomeLabel,
                questionListView,
                questionBox,
                answerLabel,
                answerListView,
                answerBox
        );

        //Scene setup for JavaFX
        Scene scene = new Scene(mainLayout, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //refreshes the answers list to add new question to display
    private void updateAnswers(int questionId) {
        List<Answer> relatedAnswers = answers.getAnswersForQuestion(questionId);
        answerObservableList.clear();

        for (Answer ans : relatedAnswers) {
            answerObservableList.add(ans.getText()); 
        }
    }

    //refreshes the question list to add new question to display
    private void refreshQuestionList() {
        questionObservableList.setAll(questions.getAllQuestions());
    }
    
    
    //edit pop up
    private String showInputDialog(String title, String message, String defaultText) {
        TextInputDialog dialog = new TextInputDialog(defaultText);
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(message);
        return dialog.showAndWait().orElse(null);
    }

    //validation alert pop up
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

//source for JavaFX Documentation: https://docs.oracle.com/javase/8/javafx/api/
