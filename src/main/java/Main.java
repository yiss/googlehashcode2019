import java.util.Optional;

public class Main {

	public static void main(String[] args) {
		SlideShow a = new SlideShow();
		SlideShow b = new SlideShow();
		SlideShow c = new SlideShow();
		SlideShow d = new SlideShow();
		SlideShow e = new SlideShow();

		a.parse("a_example.txt");
		b.parse("b_lovely_landscapes.txt");
		c.parse("c_memorable_moments.txt");
		d.parse("d_pet_pictures.txt");
		e.parse("e_shiny_selfies.txt");
	}

}
