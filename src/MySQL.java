import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Program that allows user to search database by using one of the three search
 * mods. Program connects to database, search for results and if found prints
 * them out.
 */
public class MySQL {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out
				.println("Do you want to search by:\n1. Name of the country\n2. Population number\n3. City of the state");
		boolean ok = false;
		int count = 0;
		int userChoice = 0;
		// loop that is used to make sure user enters number within allowed
		// range
		while (!ok) {
			count++;
			if (count > 1)
				System.out.println("Enter your choice");
			userChoice = input.nextInt();
			if (userChoice <= 3)
				ok = true;

		}
		// invoking method
		searchDB(userChoice);

	}

	/**
	 * method that will connect with data base and send query depending of the
	 * user choice
	 * 
	 * @param user
	 *            - choice user made(which search option will he use)
	 */
	public static void searchDB(int user) {
		Scanner input = new Scanner(System.in);
		int count = 0;
		switch (user) {
		// user chose to search by country
		case 1:
			System.out.println("Enter the name of the country:");
			String country = input.next();
			try {
				// creating connection with db
				Connection myConn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/world", "root", "user");
				Statement myStmt = myConn.createStatement();
				// we send query that selects all columns which have name user
				// entered
				ResultSet myRs = myStmt
						.executeQuery("SELECT * FROM country WHERE Name='"
								+ country + "'");
				while (myRs.next()) {
					// in case there was some result set we increment the
					// counter
					count++;
					System.out.println(myRs.getString("Name")
							+ ", population: " + myRs.getString("Population")
							+ ", continent: " + myRs.getString("Continent")
							+ ", area: " + myRs.getString("SurfaceArea"));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			// if counter is 0 there were no results found in database
			if (count == 0) {
				System.out.println("Data not found.");
			}
			break;
		// user chose to search by population number
		case 2:
			System.out
					.println("You will get list of countries with population number higher than the one you entered. Enter your number now:");
			long num = input.nextLong();
			try {
				Connection myConn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/world", "root", "user");
				Statement myStmt = myConn.createStatement();
				// we send query to country table and select all columns where
				// population is higher than the number user entered
				ResultSet myRs = myStmt
						.executeQuery("SELECT * FROM country WHERE Population > "
								+ num);
				while (myRs.next()) {
					count++;
					System.out.println(myRs.getString("Name")
							+ ", population: " + myRs.getString("Population")
							+ ", continent: " + myRs.getString("Continent")
							+ ", area: " + myRs.getString("SurfaceArea"));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			if (count == 0)
				System.out.println("Data not found.");
			break;
		// user chose to search by city name
		case 3:
			System.out.println("Enter the name of the city:");
			String cityName = input.next();
			try {
				Connection myConn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/world", "root", "user");
				Statement myStmt = myConn.createStatement();
				// we send query that selelcts all columns in city table which
				// have the name user entered
				ResultSet myRs = myStmt
						.executeQuery("SELECT * FROM city WHERE Name='"
								+ cityName + "'");
				while (myRs.next()) {
					count++;
					System.out.println(myRs.getString("Name")
							+ ", population: " + myRs.getString("Population")
							+ ", country code: "
							+ myRs.getString("CountryCode") + ", district: "
							+ myRs.getString("District"));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			if (count == 0)
				System.out.println("Data not found.");
			break;

		}
	}

}
