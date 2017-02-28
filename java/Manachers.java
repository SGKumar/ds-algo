//char* substring(char c[], int starting, int ending){
public class Manachers 
{

String substring(String s, int start, int end)
{
	return s.substring(start, end);
}

//char* preProcess(char c[]){
public static String preProcess(String s)
{
	StringBuilder str = new StringBuilder(1 + 2 * s.length());
	for(int i = 0; i < s.length(); i++) {
		str.append('#').append(s.charAt(i));
	}
	str.append('#');
    return str.toString();
}

public static String postProcess(String s)
{
	return s.replace("#", "");
}

public static void main(String[] args)
{
    String str = preProcess("jklollolkidding");
    int length = str.length();

    int[] P = new int[length];

    int C = 0;
    int R = 0;

    for(int i = 0; i < length; i++){

        int i_mirror = C - (i - C);

        if(R > i){
            P[i] = Math.min(R - i, P[i_mirror]);
        }else{
            P[i] = 0;
        }

        while (str.charAt(i + 1 + P[i]) == str.charAt(i - 1 - P[i])){
            P[i]++;
        }

        if (i + P[i] > R) {
          C = i;
          R = i + P[i];
        }

    }

    int maxLen = 0;
    int centerIndex = 0;
    for (int i = 1; i < length - 1; i++) {
        if (P[i] > maxLen) {
            maxLen = P[i];
            centerIndex = i;
        }
    }

	System.out.println(postProcess(str.substring(centerIndex - maxLen, centerIndex + maxLen)));
//    cout << postProcess(getSubstring(str, centerIndex - maxLen, centerIndex + maxLen)) << endl;

	}
}







