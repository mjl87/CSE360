package application;

public class Question {
    private int id;
    private String question;
    
    //constructor for Question object
    public Question(int id, String question) {
        this.id = id;
        this.question = question;
    }

    //setters and getters
    public int getId() {
    	return id;
    }
    
    public void setId(int id) { 
    	this.id = id; 
    }

    public String getText() { 
    	return question;
    }
    
    public void setText(String question) { 
    	this.question = question; 
    }

    @Override
    public String toString() {
        return "Question #" + id + ": " + question;
    }
}

