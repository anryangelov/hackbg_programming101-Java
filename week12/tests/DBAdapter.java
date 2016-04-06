package week12.tests;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBAdapter {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/test";

	static final String USER = "root";
	static final String PASS = "";

	private Connection conn = null;
	
	private void connect() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void closeConnection() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	private void closeStmt(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch	(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private PreparedStatement getPrepStmt(String query) {
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		if (conn != null) {
			try {
				stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		return stmt;
	}
	
	public void addQuestion(Question question) {
		String quesString = question.getQuestion();
		String query = "INSERT INTO question (question) values(?)";
		PreparedStatement stmt = null;
		try {
			connect();
			stmt = getPrepStmt(query);
			stmt.setString(1, quesString);
			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();
			resultSet.next();
			int auto_id = resultSet.getInt(1);
			closeStmt(stmt);
			query = "INSERT INTO correct_answer (answer, question_id) values(?, ?)";
			stmt = getPrepStmt(query);
			for (String answer : question.getCorrectAnswers()) {
				stmt.setInt(2, auto_id);
				stmt.setString(1, answer);
				stmt.executeUpdate();
			} 
			closeStmt(stmt);
			query = "INSERT INTO wrong_answer (answer, question_id) values(?, ?)";
			stmt = getPrepStmt(query);
			for (String answer : question.getWrongAnswers()) {
				stmt.setInt(2, auto_id);
				stmt.setString(1, answer);
				stmt.executeUpdate();
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
			closeStmt(stmt);
		}
		
	}
	
	public Question getQuestion(int id) {
		Question res = null;
		String q;
		List<String> correctAnswers = new ArrayList<String>();
		List<String> wrongAnswers = new ArrayList<String>();
		String queryQuestion = "Select question from question where id = ?";
		String queryCorrectAnswer = "Select answer from correct_answer where question_id = ?";
		String queryWrongAnswer = "Select answer from wrong_answer where question_id = ?";
 		PreparedStatement stmt = null;
		try {
			connect();
			stmt = getPrepStmt(queryQuestion);
			stmt.setInt(1, id);
			ResultSet resultSet = stmt.executeQuery();
			resultSet.next();
			q = resultSet.getString(1);
			closeStmt(stmt);
			resultSet.close();
			stmt = getPrepStmt(queryCorrectAnswer);
			stmt.setInt(1, id);
			resultSet = stmt.executeQuery();
			String temp;
			while (resultSet.next()) {
				temp = resultSet.getString(1);
				correctAnswers.add(temp);
			}
			closeStmt(stmt);
			resultSet.close();
			stmt = getPrepStmt(queryWrongAnswer);
			stmt.setInt(1, id);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				temp = resultSet.getString(1);
				wrongAnswers.add(temp);
			}
			closeStmt(stmt);
			resultSet.close();
			res = new Question(q, correctAnswers, wrongAnswers);
			closeConnection();
		} catch	(SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public List<Integer> getQuestiosIDs() {
		List<Integer> res = new ArrayList<Integer>();
		String query = "Select id from question"; 
		try {
			connect();
			PreparedStatement stmt = getPrepStmt(query);
			stmt.executeQuery();
			ResultSet resulSet = stmt.getResultSet();
			while (resulSet.next()) {
				res.add(resulSet.getInt(1));
			}
			closeStmt(stmt);
			closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static void main(String[] args) {
		
		DBAdapter db = new DBAdapter();
		/*
		List<String> correctAnswers = Arrays.asList("Sofia");
		List<String> wrongAnswers = Arrays.asList("Plovdiv", "Varna");
		Question question = new Question("The capital of Bg", correctAnswers, wrongAnswers);
		db.addQuestion(question);
		*/
		/*
		Question q = db.getQuestion(13);
		System.out.println(q);
		*/
		System.out.println(db.getQuestiosIDs());
	}
	
	
}
