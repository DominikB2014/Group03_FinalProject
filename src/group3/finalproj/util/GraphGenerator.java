package group3.finalproj.util;

import java.util.ArrayList;

import group3.finalproj.car.*;
import group3.finalproj.io.ReadData;

/**
 * 
 * @author Pedram Yazdinia
 *
 */
public class GraphGenerator {
	
	public static Graph graphMake(int n) {
		Graph G = new Graph(ReadData.cars.size());
		for (int i = 0; i < ReadData.cars.size(); i++) {
			for (int j = i + 1; j < ReadData.cars.size(); j++) {
				int count = 0;
				for (Property p: Property.values()) {
					if (ReadData.cars.get(i).hasProperty(p) && ReadData.cars.get(j).hasProperty(p) 
							&& ReadData.cars.get(i).scoreCalc(p) < 11 && ReadData.cars.get(i).scoreCalc(p) > 5
							&& ReadData.cars.get(j).scoreCalc(p) < 11 && ReadData.cars.get(j).scoreCalc(p) > 5
							&& calcableProperty(p)) {
						count++;
					}	
				}
				if (count == n) {
					G.addEdge(i, j);
				}
			}
		}
		return G;
	}
	
	public static ArrayList<Tuple<Car, Integer>> runDFS(Graph G, int source, int maxPrice, ArrayList<Tuple<Property, Integer>> property_Rank){
		ArrayList<Tuple<Car, Integer>> carTuples = new ArrayList<Tuple<Car, Integer>>(); 
		BreadthFirstPaths bfs = new BreadthFirstPaths(G, source);
		boolean[] listCar = bfs.getMarked();
		for (int i = 0; i < listCar.length; i++) {
			if (listCar[i]) {
				int score = ReadData.cars.get(i).scoreCalc(property_Rank, maxPrice);
				Tuple<Car, Integer> tempTup = new Tuple<Car, Integer>(ReadData.cars.get(i), score);
				carTuples.add(tempTup);
			}
		}
		return carTuples;
	}
	
	public static ArrayList<Car> theBestFive(ArrayList<Tuple<Car, Integer>> carTuples){
		System.out.println("Potential Cars: " + carTuples.size());
		ArrayList<Car> bestCar = new ArrayList<Car>();
		for (Tuple<Car, Integer> tuple: carTuples) {
			tuple.getProperty().addProperty(Property.Score, tuple.getRank());
			bestCar.add(tuple.getProperty());
		}
		Heap.sort(bestCar, Property.Score);
		ArrayList<Car> fiveBest = new ArrayList<Car>();
		for (int i = bestCar.size() - 1; i < bestCar.size() && i > bestCar.size()-6; i--) {
			fiveBest.add(bestCar.get(i));
		}
		return fiveBest;
	}
	
	   /***************************************************************************
	    * Helper functions
	    ***************************************************************************/
	
	private static  boolean calcableProperty(Property p) {
		return !(p.equals(Property.BodyStyle) || p.equals(Property.HighwayMPG) || p.equals(Property.Link) 
				|| p.equals(Property.Transmission) || p.equals(Property.Trim) || p.equals(Property.Year)
				|| p.equals(Property.Model) || p.equals(Property.FuelType));
	}
	
	private static int carIndex(Car c) {
		for (int i = 0 ; i < ReadData.cars.size(); i++) {
			if (ReadData.cars.get(i) == c) {
				return i;
			}
		}
		return -1;
	}
	
	   /***************************************************************************
	    * Main method for testing
	    ***************************************************************************/
	
	public static void main(String args[]) {
		int source = 3;
		
		ArrayList<CarType> types = new ArrayList<CarType>(); //Must be in sorted order!	
		types.add(CarType.Coupe);
		types.add(CarType.Sedan);
		ReadData.readCars("data/newCars.csv", types, 0, 40000);
		
		ArrayList<Tuple<Property, Integer>> properties = new ArrayList<Tuple<Property, Integer>>();
		properties.add(new Tuple<Property, Integer>(Property.Make, 9));
		
		System.out.print("Properties Selected: ");
		for(Tuple<Property, Integer> tuple: properties) System.out.print(tuple + " ");
		System.out.println("\nSource Node: " + ReadData.cars.get(source));
		
		ArrayList<Car> cars = new ArrayList<Car>();
		Graph G = GraphGenerator.graphMake(3);
		
		System.out.println("Recommended Cars");
		cars = GraphGenerator.theBestFive(GraphGenerator.runDFS(G, source, 40000, properties));
		for (Car car: cars) {
			System.out.println(car);
		}
	}
}
