package week12.tests;
import java.util.List;

public class Question {

	private final String question;
	private final List<String> correctAnswers;
	private final List<String> wrongAnswers;
		
	public Question(String question, List<String> correctAnswers,
			List<String> wrongAnswers) {
		this.question = question;
		this.correctAnswers = correctAnswers;
		this.wrongAnswers = wrongAnswers;
	}
	
	public String toString() {
		return String.format("question:\n%s\ncorrect answers:\n%s\nwrong answers:\n%s\n", 
				question, correctAnswers, wrongAnswers);
	}

	public List<String> getCorrectAnswers() {
		return correctAnswers;
	}


	public String getQuestion() {
		return question;
	}


	public List<String> getWrongAnswers() {
		return wrongAnswers;
	}
	
}
