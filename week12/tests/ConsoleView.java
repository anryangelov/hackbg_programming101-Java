package week12.tests;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ConsoleView {
	
	QuestionControler qc = new QuestionControler();
	
	private String getQuestionString(Question question, List<Integer> positonsCorrectAnswer) {
		String res = question.getQuestion() + "\n";
		int option = 0;
		int indexCorrectAnswer = 0;
		for (String wrongA : question.getWrongAnswers()) {
			option++;
			while (positonsCorrectAnswer.contains(option)) {
				String correctA = question.getCorrectAnswers().get(indexCorrectAnswer);
				res += String.format("%s - %s\n", option, correctA);
				option++;
				indexCorrectAnswer++;
			}
			res += String.format("%s - %s\n", option, wrongA);
		}
		option++;
		if (positonsCorrectAnswer.contains(option)) {
			String correctA = question.getCorrectAnswers().get(indexCorrectAnswer);
			res += String.format("%s - %s\n", option, correctA);
		}
		return res;
	}
	
	public boolean checkResult(List<Integer> corretOptions, List<Integer> chosenOptions) {
		if (corretOptions.size() != chosenOptions.size()) {
			return false;
		}
		for (Integer integer : chosenOptions) {
			if (!corretOptions.contains(integer)) {
				return false;
			}
		}
		return true;
	}
	
	public List<Integer> makeListOfInteger(String [] arr) {
		List<Integer> res = new ArrayList<Integer>();
		for (String string : arr) {
			res.add(Integer.parseInt(string));
		}
		return res;
	}
	
	public void start() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Choose right option\n");
		for (Question question : qc.getQuestions(5, 4, 1)) {
			List<Integer> correctPossions = qc.chooseCorrectPossion(question, 1);
			String text = getQuestionString(question, correctPossions) + "\n-> ";
			System.out.print(text);
			String option;
			option = scanner.nextLine();
			String [] options = option.split("\\s+");
			boolean result = false;
			while (true) {
				try {
					result = checkResult(correctPossions, makeListOfInteger(options));
				} catch (java.lang.NumberFormatException e) {
					System.out.print("please enter integer\n-> ");
					option = scanner.nextLine();
					options = option.split("\\s+");
					continue;
				}
				break;
			}
			if (result == true) {
				System.out.println("Correct!\n");
			} else {
				System.out.println("Incorrect\n");
			}
		}
		scanner.close();
	}
	
	
	public static void main(String[] args) {
		
		/*
		QuestionControler qc = new QuestionControler();
		ConsoleView console = new ConsoleView();
		Question question = qc.getQuestions(1, 5, 1).get(0);
		List<Integer> correctPos = qc.chooseCorrectPossion(question, 1);
		String q = console.getQuestionString(question, correctPos);
		System.out.println(correctPos);
		System.out.println(q);
		*/ 
		
		ConsoleView con = new ConsoleView();
		con.start();
	}

}
