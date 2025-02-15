package application;

import java.util.ArrayList;
import java.util.List;

public class Answers {
	//create list of answer objects and create array list to hold Answers
    private List<Answer> answer_list = new ArrayList<>();
    //answer identifier
    private int answer_ID = 1;

    //add answer
    public void addAnswer(int question_ID, String answer_text) {
        answer_list.add(new Answer(answer_ID, question_ID, answer_text));
        //increment ID
        answer_ID++;
    }
    
    //edit answer
    public void editAnswer(int ID, String update) {
        boolean flag = false;
        for (int i = 0; i < answer_list.size(); i++) {
            if (answer_list.get(i).getId() == ID) {
                answer_list.get(i).setText(update);
                flag = true;
                System.out.println("Answer ID updated");
                break;
            }
        }
      //validation - answer found?
        if (!flag) {
            System.out.println("Answer ID not found!");
        }
    }
    
    //delete answer
    public void deleteAnswer(int answer_id) {
        boolean flag = false;
        for (int i = 0; i < answer_list.size(); i++) {
            if (answer_list.get(i).getId() == answer_id) {
                answer_list.remove(i);
                flag = true;
                System.out.println("Answer ID deleted");
                break;
            }
        }
      //validation - answer found?
        if (!flag) {
            System.out.println("Answer ID not found!");
        }
    }
    
    //search answer
    public List<Answer> searchAnswer(String keyword) {
        //create list to store search results
        List<Answer> search_match = new ArrayList<>();

        //search for keyword in answers
        for (int i = 0; i < answer_list.size(); i++) {
            if (answer_list.get(i).getText().toLowerCase().contains(keyword.toLowerCase())) {
                search_match.add(answer_list.get(i));
            }
        }

        if (search_match.isEmpty()) {
            System.out.println("No search results found");
        }

        return search_match;
    }
    
    //get all answers for question
    public List<Answer> getAnswersForQuestion(int question_ID) {
    	//create list to store answers for indiv question
        List<Answer> answers_for_question = new ArrayList<>();

        for (int i = 0; i < answer_list.size(); i++) {
            if (answer_list.get(i).getQuestionId() == question_ID) {
                answers_for_question.add(answer_list.get(i));
            }
        }

        return answers_for_question;
    }
    
    //display all answers
    public List<Answer> getAllAnswers() {
        return new ArrayList<>(answer_list); 
    }
}

