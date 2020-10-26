import java.io.*;
import java.util.*;
import acm.program.*;

public class FlightPlanner extends ConsoleProgram {
	
	private HashMap<String, ArrayList<String>> flights;
	private ArrayList<String> cities;

	public void run() {

		flights = new HashMap<String, ArrayList<String>>();
		cities = new ArrayList<String>();

		readFile("flights.txt");

		System.out.println("Welcome to Flight Planner!");
		System.out.println("Here's a list of all the cities in our database: ");
		for (String key : flights.keySet()) {
			System.out.println(key);
		}

		System.out.println("Let's plan a round-trip route!");
		String startingCity = getStartingCity();

		planRoute(startingCity);

		printRoute();
	}

	private void printRoute() {

		String route = "";

		for (int i = 0; i < cities.size(); i++) {
			route += cities.get(i) + " -> ";
		}
		route += cities.get(0);

		System.out.println("The route you've chosen is: ");
		System.out.println(route);
	}

	private String getStartingCity() {

		String startingCity = readLine("Enter the starting city: ");

		while (true) {
			for (String key : flights.keySet()) {
				if (key.toLowerCase().equals(startingCity.toLowerCase())) {
					return key;
				}
			}
			startingCity = readLine("Please select a destination from the list: ");
		}
	}

	private void planRoute(String startingCity) {

		String departureCity = startingCity;

		while (true) {

			cities.add(departureCity);

			System.out.println("From " + departureCity + " you can fly directly to:");
			for (String destination : flights.get(departureCity)) {
				System.out.println(destination);
			}

			departureCity = getSelectedDestination(departureCity);

			if (departureCity.equals(startingCity)) {
				break;
			}
		}
	}

	private String getSelectedDestination(String departureCity) {

		String selectedDestination = readLine("Where do you want to go from " + departureCity + "? ");

		while (true) {
			for (String destination : flights.get(departureCity)) {
				if (destination.toLowerCase().equals(selectedDestination.toLowerCase())) {
					return destination;
				}
			}
			selectedDestination = readLine("Please select a destination from the list: ");
		}

	}

	private void readFile(String fileName) {
		try {
			BufferedReader rd = new BufferedReader(new FileReader(fileName));

			while (true) {
				String line = rd.readLine();
				if (line == null)
					break;
				String key = line.substring(0, line.indexOf(" -> "));
				String destination = line.substring(line.indexOf(" -> ") + 4);
				if (flights.containsKey(key)) {
					flights.get(key).add(destination);
				} else {
					ArrayList<String> aList = new ArrayList<String>();
					aList.add(destination);
					flights.put(key, aList);
				}
			}
		} catch (IOException ex) {
			System.out.println("File not found.");
		}
	}

}