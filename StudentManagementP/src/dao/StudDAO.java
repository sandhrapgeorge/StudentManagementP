package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.InvalidInputException;
import vo.Student;

public class StudDAO {

	private Connection connnection;
	private PreparedStatement p;
	private ResultSet resultSet;
	private String query;
	private List<Student> studentList = new ArrayList<>();

	public void connect() throws Exception {

		final String url = "jdbc:mysql://localhost:3306/studentDB";
		final String userName = "root";
		final String password = "Root_1234";
		Class.forName("com.mysql.cj.jdbc.Driver");
		connnection = DriverManager.getConnection(url, userName, password);
	}

	public boolean existStudent(final int id) throws Exception {

		query = String.format("select id from Student where id=%d", id);
		p = connnection.prepareStatement(query);
		resultSet = p.executeQuery();
		if (resultSet.next()) {
			p.close();
			return true;
		}
		return false;
	}

	public void insert(final Student s) throws Exception {

		final int id = s.getId();
		if (existStudent(id)) {
			throw new InvalidInputException("Student id already exist");
		}
		query = "insert into Student values(?,?,?)";
		p = connnection.prepareStatement(query);
		p.setInt(1, id);
		p.setString(2, s.getName());
		p.setInt(3, s.getAge());
		p.executeUpdate();
		p.close();
	}

	public void delete(final int id) throws Exception {

		if (!existStudent(id)) {
			p.close();
			throw new InvalidInputException("Id does not exist");
		}

		query = String.format("delete from Student where id=%d", id);
		p = connnection.prepareStatement(query);
		p.executeUpdate();
		p.close();

	}

	public List<Student> readByPage(final int pageSize, final int fromIndex) throws Exception {

		query = String.format("select * from Student limit %d offset %d", pageSize, fromIndex);
		p = connnection.prepareStatement(query);
		resultSet = p.executeQuery();
		studentList.clear();

		while (resultSet.next()) {
			final int id = resultSet.getInt("id");
			final String name = resultSet.getString("name");
			final int age = resultSet.getInt("age");
			final Student s = new Student(id, name, age);
			studentList.add(s);
		}
		p.close();
		return studentList;
	}

	public void update(final Student student) throws Exception {

		if (!existStudent(student.getId())) {
			p.close();
			throw new InvalidInputException("Given student id does not exist");
		}

		final int id = student.getId();
		final String name = student.getName();
		final int age = student.getAge();
		query = String.format("update Student set name='%s',age=%d where id=%d", name, age, id);
		p = connnection.prepareStatement(query);
		p.executeUpdate();
		p.close();

	}

	public List<Student> searchByName(final String pattern) throws Exception {

		query = "select * from Student where name like ?";
		p = connnection.prepareStatement(query);
		p.setString(1, "%" + pattern + "%");

		resultSet = p.executeQuery();
		studentList.clear();
		while (resultSet.next()) {
			final int id = resultSet.getInt("id");
			final String name = resultSet.getString("name");
			final int age = resultSet.getInt("age");
			final Student s = new Student(id, name, age);
			studentList.add(s);
		}
		p.close();
		return studentList;

	}

	public Student readByName(final String name) throws Exception {

		query = "select * from Student where name=?";
		p = connnection.prepareStatement(query);
		p.setString(1, name);
		resultSet = p.executeQuery();
		if (!resultSet.next()) {
			p.close();
			throw new InvalidInputException("Name not found");
		}
		final int id = resultSet.getInt("id");
		final String name1 = resultSet.getString("name");
		final int age = resultSet.getInt("age");
		final Student s = new Student(id, name1, age);
		p.close();
		return s;

	}

	public void closeConnection() throws Exception {
		connnection.close();

	}
}
