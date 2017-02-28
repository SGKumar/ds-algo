package ctci;

import java.util.Date;

import llsq.Queue;

// Stacks and Queues
public class CTCICh3
{
	private static abstract class Pet
	{
		private long order;
		private String name;
		public Pet(String name)
		{
			this.name = name;
			this.order = new Date().getTime();
		}
		public long Order()
		{
			return order;
		}
		public String Name()
		{
			return name;
		}
	}
	private static class Dog extends Pet
	{
		public Dog(String name, int order) 
		{
			super(name);
		}
	}
	private static class Cat extends Pet
	{
		public Cat(String name, int order) 
		{
			super(name);
		}
	}
	private static class PetQueue
	{
		Queue<Dog> dogQ;
		Queue<Cat> catQ;
		
		public PetQueue()
		{
			dogQ = new Queue<Dog>();
			catQ = new Queue<Cat>();
		}
		public void enque(Pet pet)
		{
			if(pet instanceof Cat) {
				catQ.add((Cat)pet);
			}
			else {
				dogQ.add((Dog)pet);
			}
		}
	}
}
