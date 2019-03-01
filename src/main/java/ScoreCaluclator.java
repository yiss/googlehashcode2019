import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ScoreCaluclator {
	public static int calculateScore(List<Slide> slides, String resultFile) {
		try {
			int score = 0;
			List<Slide> transitionedSlides = new ArrayList<>();
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(resultFile)));
			int entriesSize = Integer.parseInt(bufferedReader.readLine());
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] parsed = line.split(" ");
				int id = Integer.parseInt(parsed[0]);
				transitionedSlides.add(getSlideByPhotoId(id, slides));
			}

			for (int i=0; i<transitionedSlides.size()-1; i++) {
				score += calculateInterestFactor(transitionedSlides.get(i), transitionedSlides.get(i+1));
			}
			System.out.println("Score is : "+score);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static Slide getSlideByPhotoId(int id, List<Slide> slides) {
		for (int i=0; i<slides.size(); i++) {
			if (slides.get(i).getFirst().getId() == id) {
				return slides.get(i);
			} else if (slides.get(i).getSecond().isPresent() && slides.get(i).getSecond().get().getId() == id){
				return slides.get(i);
			}
		}
		return null;
	}

	public static int calculateInterestFactor(Slide a, Slide b) {
		SetView<String> intersection = Sets.intersection(a.getSlideTags(), b.getSlideTags());
		SetView<String> diffA = Sets.difference(a.getSlideTags(), intersection);
		SetView<String> diffB = Sets.difference(b.getSlideTags(), intersection);
		return Math.min(intersection.size(), Math.min(diffA.size(), diffB.size()));
	}

}
