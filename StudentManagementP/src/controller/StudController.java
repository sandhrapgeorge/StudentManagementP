package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import service.StudService;

//import java.io.DataInputStream;
public class StudController {

	public static void main(final String[] args) {
		// TODO Auto-generated method stub
		try {
			final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			final StudService studService = new StudService();
			studService.connect();
			boolean flag = true;
			while (flag) {
				System.out.println("\n1.Insert student");
				System.out.println("2.Delete student by id");
				System.out.println("3.Print all student information with pagination");
				System.out.println("4.Update student information");
				System.out.println("5.Search by name");
				System.out.println("6.Read by name");
				System.out.println("7.Exit");
				System.out.print("Enter your choice : ");
				final int option = Integer.parseInt(br.readLine());
				switch (option) {
				case 1:
					StudService.insert();
					break;

				case 2:
					StudService.delete();
					break;

				case 3:
					StudService.readByPage();
					break;

				case 4:
					StudService.update();
					break;

				case 5:
					StudService.searchByName();
					break;

				case 6:
					StudService.readByName();
					break;
				case 7:
					flag = false;
					studService.closeConnection();
					break;

				default:
					System.out.println("Enter a valid option");
					break;
				}
			}

		} catch (final Exception e) {
			System.out.println("Enter a valid option");
		}

	}
}
