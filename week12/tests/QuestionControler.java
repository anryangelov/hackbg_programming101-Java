package week12.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class QuestionControler {
	
	DBAdapter db;
	Random random = new Random();
	
	public QuestionControler() {
		db = new DBAdapter();
	}
	
	public List<Question> getQuestions(int max, int numWrongAnswers, int numCorrectAnswers) {
		List<Question> res = new ArrayList<Question>();
		List<Integer> questions_id = db.getQuestiosIDs();
		int question_id;
		Question question;
		for (int i = 0; i < max; i++) {
			question_id = random.nextInt(questions_id.size());
			question = db.getQuestion(questions_id.get(question_id));
			res.add(processQueston(question, numWrongAnswers, numCorrectAnswers));
			questions_id.remove(question_id);
		}
		return res;
	}
	
	private Question processQueston(Question question, int numWrongAnswers, int numCorrectAnswers) {
		while (question.getCorrectAnswers().size() > numCorrectAnswers) {
			int random_int = random.nextInt(question.getCorrectAnswers().size());
			question.getCorrectAnswers().remove(random_int);
		}
		Collections.shuffle(question.getWrongAnswers());
		while (question.getWrongAnswers().size() > numWrongAnswers) {
			int random_int = random.nextInt(question.getWrongAnswers().size());
			question.getWrongAnswers().remove(random_int);
		}
		Collections.shuffle(question.getCorrectAnswers());
		return question;
	}
	
	public void addQuestion(Question question) {
		db.addQuestion(question);
	}
	
	public List<Integer> chooseCorrectPossion(Question q, int numCorrectPossion){
		ArrayList<Integer> res = new ArrayList<Integer>();
		ArrayList<Integer> options = new ArrayList<Integer>();
		for (int i = 0; i < (q.getCorrectAnswers().size() + q.getWrongAnswers().size()); i++) {
			options.add(i + 1);
		}
		Collections.shuffle(options);
		for (int i = 0; i < numCorrectPossion; i++) {
			res.add(options.get(i));
		}
		return res;
	}
	
	
	public static void main(String[] args) {
		
		
		QuestionControler qc = new QuestionControler();
		/*
		String questionString = "capital of Spain"; 
		List<String> correctAnswers = Arrays.asList("Madrid");
		List<String> wrongAnswers = Arrays.asList("Barcelona", "Vienna", "New York", "Atina", "Berlin", "London");
		Question questionObj = new Question(questionString, correctAnswers, wrongAnswers);
		qc.addQuestion(questionObj);
		*/
		
		List<Question> questions = qc.getQuestions(5, 5, 1);
		for (Question question : questions) {
			System.out.println(question);
		}
		
		/*
		List<String> l = Arrays.asList("1", "2", "3", "4");
		Collections.shuffle(l);
		System.out.println(l);
		*/
	}
	
	
	
}
