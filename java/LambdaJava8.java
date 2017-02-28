import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class LambdaJava8
{
	// http://www.javacodegeeks.com/2014/05/java-8-features-tutorial.html#new_features
	private static void functionalInterfaces()
	{
		// 2.1. Lambdas and Functional Interfaces
		System.out.println("LambdaJava8");
		Arrays.asList("a", "b", "c").forEach((String e) -> System.out.print(e + " "));
		System.out.println();
		Arrays.asList("a", "b", "c").forEach(e -> System.out.print(e + "  "));
		System.out.println();
	}
	private static void optionalFeatures()
	{
		// 4. New Features in Java libraries
		// 4.1. Optional
		Optional< String > fullName = Optional.ofNullable( null );
		System.out.println( "Full Name is set? " + fullName.isPresent() );
		System.out.println( "Full Name: " + fullName.orElseGet( () -> "[none]" ) );
		System.out.println( fullName.map( s -> "Hey " + s + "!" ).orElse( "Hey Stranger!" ) );

		Optional< String > firstName = Optional.of( "Tom" );
		System.out.println( "First Name is set? " + firstName.isPresent() );
		System.out.println( "First Name: " + firstName.orElseGet( () -> "[none]" ) );
		System.out.println( firstName.map( s -> "Hey " + s + "!" ).orElse( "Hey Stranger!" ) );
	}

	interface Printer
	{
		void print(String str);
	}

	public void printSomething(Printer printer, String something)
	{
		//System.out.println(something);
		printer.print(something);
	}

    public static void main(String args[])
    {
		functionalInterfaces();
		optionalFeatures();

		LambdaJava8 demo = new LambdaJava8();

		String something = "I'm using a Functional interface"; //"I am learning Lambda";
		/*Printer printer = new Printer() {
			@Override
			public void print(String str) {
				System.out.println(str);
			}
		};*/
		//Printer printer = (String str) -> { System.out.println(str); };
		//Printer printer = (str) -> { System.out.println(str); };

		/*
		Printer printer = str ->  System.out.println(str);
		demo.printSomething(printer, something);
		*/
		demo.printSomething(str ->  System.out.println(str), something);

    }
}
