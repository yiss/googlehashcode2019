import java.util.Optional;

public class Main {

	public static void main(String[] args) {
		SlideShow slideShow = new SlideShow();
		slideShow.parse("a_example.txt");
//		slideShow.parse("b_lovely_landscapes.txt");
		slideShow.parse("c_memorable_moments.txt");
	}

}
