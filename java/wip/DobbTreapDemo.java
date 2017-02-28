import java.util.Enumeration;

class Year implements DobbOrdered
{
   int year;   
   Year(int y) { year = y; }
   public int compareTo(DobbOrdered y) {
      if (year > ((Year) y).year)
         return 1;
      else if (year < ((Year) y).year)
         return -1;
      else
         return 0;
   }
   public String toString() {
      return Integer.toString(year);
   }
}

class DobbTreapDemo
{
	public static void main(String[] args) {
		DobbTreap treap = new DobbTreap();

		treap.put(new Year(1935), "Elvis Presley");
		treap.put(new Year(1926), "Chuck Berry");
		treap.put(new Year(1941), "Bob Dylan");
		treap.put(new Year(1936), "Roy Orbison");
		treap.put(new Year(1915), "Muddy Waters");

		Enumeration k = treap.keys();
		Enumeration e = treap.elements();
		while (k.hasMoreElements()) {
			System.out.print(k.nextElement() + ": ");
			System.out.println(e.nextElement());
		}
		
		for(int i = 0; i < 50; i++)	{
			treap.put(new Year(1900 + i), "Bob Dylan");
		}
		
		treap.printDebug();

	}
}
