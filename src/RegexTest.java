import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexTest {

	public static void main(String[] args) {
		Pattern p = Pattern.compile("(a)(b)c");
		Matcher m = p.matcher("abcabababc");
		while (m.find()){
			System.out.println("Group 0 : " + m.group(1) + " Group 1 " + m.group(2));
		}
	}

}
