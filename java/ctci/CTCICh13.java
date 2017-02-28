package ctci;

import java.util.List;
import java.util.ArrayList;

public class CTCICh13
{
	private static class Country
	{
		private String name;
		private int size;
		private String continent;
		public Country(String n, String c, int sz)
		{
			name = n;
			size = sz;
			continent = c;
		}
		
		public String getName() 
		{
			return name;
		}
		
		public String getContinent() 
		{
			return continent;
		}
		
		public int getPopulation()
		{
			return size;
		}
	}
	
	public static int getPopulation(List<Country> countries, String continent)
	{
		return countries.stream()
				.filter(country -> country.getContinent().equals(continent))
				.mapToInt(Country::getPopulation)
				.sum();
	}
	public static void main(String[] args)
	{
		ArrayList<Country> countries = new ArrayList<>();
		countries.add(new Country("India", "America", 4));
		countries.add(new Country("China", "America", 6));
		countries.add(new Country("Russia", "America", 5));
		countries.add(new Country("Lanka", "America", 1));
		countries.add(new Country("US", "America", 3));
		countries.add(new Country("Nepal", "America", 2));
		countries.add(new Country("Singa", "Chumma", 25));
		
		System.out.println("Lambda calling " + getPopulation(countries, "America"));
	}
}
