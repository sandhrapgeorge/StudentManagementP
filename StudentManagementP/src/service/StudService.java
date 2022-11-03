package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import dao.StudDAO;
import exception.InvalidInputException;
import vo.Student;

public class StudService {
	// inside Student service class

	private static StudDAO studentDAO = new StudDAO();
	final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public void connect() throws Exception {
		studentDAO.connect();
	}

	public static int readInteger() throws Exception {
		final int i = Integer.parseInt(br.readLine());
		return i;
	}

	public static String readString() throws Exception {
		final String s = br.readLine();
		return s;
	}

	// Insert student
	public static void insert() {

		try {

			System.out.println("Enter Id, Name and Age of the student");
			final int id = StudService.readInteger();
			if (id < 1) {
				throw new InvalidInputException("Invalid student Id");
			}
			final String name = StudService.readString();
			final String regex = "^[a-zA-Z]*$";
			if (!name.matches(regex)) {
				throw new InvalidInputException("The given string is not a proper name");
			}
			final int age = StudService.readInteger();
			if ((age < 6) || (age > 50)){
				throw new InvalidInputException("Invalid student Age");
			}
			final Student student = new Student(id, name, age);
			studentDAO.insert(student);
			System.out.println("Student record inserted successfuly\n");

		} catch (final Exception e) {
			System.out.println(e.getMessage());
			// System.out.println("Invalid input");
		}
	}

	// Delete student using student id
	public static void delete() {

		try {

			System.out.println("Enter the student Id");
			final int id = StudService.readInteger();
			studentDAO.delete(id);
			System.out.println("Student record deleted successfully\n");
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

	}

	// Print all student information with pagination
	public static void readByPage() {

		try {

			System.out.println("Enter the page number");
			final int pageNumber = StudService.readInteger();
			if (pageNumber < 1) {
				throw new InvalidInputException("Invalid page number");
			}
			final int pageSize = 2;

			final int fromIndex = pageSize * (pageNumber - 1);
			final List<Student> subList = studentDAO.readByPage(pageSize, fromIndex);

			if (subList.isEmpty()) {
				throw new InvalidInputException("No data found in the given page");
			}

			for (final Student student : subList) {
				display(student);
			}
			System.out.println();
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// update student record based on student id
	public static void update() {

		try {

			System.out.println("Enter the id of the student whose record need to update");
			final int id = StudService.readInteger();
			if (id < 1) {
				throw new InvalidInputException("Invalid student Id");
			}
			System.out.println("Enter the name and age");
			final String name = StudService.readString();
			final String regex = "^[a-zA-Z]*$";
			if (!name.matches(regex)) {
				throw new InvalidInputException("The given string is not a proper name");
			}
			final int age = StudService.readInteger();
			if (age < 5 || age > 50) {
				throw new InvalidInputException("Invalid student Age");
			}
			final Student student = new Student(id, name, age);
			studentDAO.update(student);
			System.out.println("Student record updated Successfully\n");
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void searchByName() {

		try {

			System.out.println("Enter the name to search");
			final String name = StudService.readString();
			final List<Student> studentList = studentDAO.searchByName(name);
			if (studentList.isEmpty()) {
				System.out.println("No data record found");
				return;
			}

			for (final Student stud : studentList) {
				display(stud);
			}
			System.out.println();
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void readByName() {

		try {

			System.out.println("Enter the Name");
			final String name = StudService.readString();
			final Student stud = studentDAO.readByName(name);
			display(stud);
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void display(final Student stud) {

		System.out.println("Student Id : " + stud.getId());
		System.out.println("Student Name : " + stud.getName());
		System.out.println("Student Age : " + stud.getAge());
		System.out.println();
	}

	public void closeConnection() throws Exception {
		studentDAO.closeConnection();
	}

}
