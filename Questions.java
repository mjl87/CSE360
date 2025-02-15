package application;

import java.util.ArrayList;
import java.util.List;

public class Questions {
	//create list of question objects and create array list to hold questions
    private List<Question> question_list = new ArrayList<>();
    //question identifier
    private int question_ID = 1;

    //add question
    public void addQuestion(String question) {
        //validation - empty question
    	if (question == null) {
            System.out.println("Error: Question cannot be empty");
            return;
        }
    	question_list.add(new Question(question_ID, question));
    	System.out.println("Question added with ID: " + question_ID);
    	//increment ID
    	question_ID++;
    }
    
    //edit question
    public void editQuestion(int ID, String update) {
    	boolean flag = false;
        //validation - empty question
    	if (update == null) {
            System.out.println("Error: Question cannot be empty");
            return;
        }
    	for(int i = 0; i < question_list.size(); i++) {
    		if (question_list.get(i).getId() == ID) {
    			question_list.get(i).setText(update);
    			flag = true;
    			System.out.println("Question ID updated");
    			break;    			
    		}
    	}
    	if (!flag) {
    		System.out.println("Question ID not found!");
    	}
    }
        
    //delete question
    public void deleteQuestion(int question_id) {
    	boolean flag = false;
    	for(int i = 0; i < question_list.size(); i++) {
    		if (question_list.get(i).getId() == question_id) {
    			question_list.remove(i);
    			flag = true;
    			System.out.println("Question ID deleted");
    			break;    			
    		}
    	}
        //validation - question found?
    	if (!flag) {
    		System.out.println("Question ID not found!");
    	}
    }
    
   
    //display all questions
    public void displayAllQuestions() {
        if (question_list.isEmpty()) {
            System.out.println("No questions");
        } else {
            for (int i = 0; i < question_list.size(); i++) { 
                System.out.println(question_list.get(i).getId() + ": " + question_list.get(i).getText());
            }
        }
    }
    
    // display all questions attempt 2
    public List<Question> getAllQuestions() {
        return new ArrayList<>(question_list); 
    }

    
}
    
    
   
