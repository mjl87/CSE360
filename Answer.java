package application;

public class Answer {
    private int id; // answer ID
    private int question_id; // ID of question being answered
    private String answer;

    //constructor for Question object	
    public Answer(int id, int question_id, String answer) {
        this.id = id;
        this.question_id = question_id;
        this.answer = answer;
    }
    
    //setters and getters
    public int getId() {
    	return id;
    }
    
    public void setId(int id) {
    	this.id = id;
    }

    public int getQuestionId() { 
    	return question_id;
    }
    
    public void setQuestionId(int question_id) {
    	this.question_id = question_id;
    }

    public String getText() { 
    	return answer;
    }
    
    public void setText(String answer) {
    	this.answer = answer;
    }

    @Override
    public String toString() {
        return "Answer" + id + " (Q" + question_id + "): " + answer;
    }
}

