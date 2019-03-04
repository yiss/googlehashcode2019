import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.log4j.Logger;

public class SlideShow {

	private List<Slide> slides;
	private Set<String> allTags;
	private PhotoBuilder builder;
	private HashMap<String, List<List<Integer>>> tagsDic;
	private Logger logger = Logger.getLogger(SlideShow.class.getName());


	public SlideShow() {
		this.slides = new ArrayList<>();
		this.allTags = new HashSet<>();
		this.builder = new PhotoBuilder();
	}

	public void parse(String fileName) {
		try {
			BufferedReader bufferedReader = new BufferedReader(
				new FileReader(new File("in/" + fileName)));
			String line = bufferedReader.readLine();
//			ArrayList<Photo> photos = new ArrayList<>();

			List<Photo> horizontalPhotos = new ArrayList<>();
			List<Photo> verticalPhotos = new ArrayList<>();
			while ((line = bufferedReader.readLine()) != null) {

				Photo photo = builder.build(line.split(" "));

				if (photo.getOrientation() == Orientation.V) {
					verticalPhotos.add(photo);
				} else {
					horizontalPhotos.add(photo);
				}
			}

			bufferedReader.close();



			logger.info(String.format("[INFO] : number of photos %d",
				(verticalPhotos.size() + horizontalPhotos.size())));
			logger.info(
				String.format("[INFO] : number of vertical photos %d", verticalPhotos.size()));
			logger.info(
				String.format("[INFO] : number of horizontal photos %d", horizontalPhotos.size()));

			getVerticalSlides(verticalPhotos);
			getHorizontalSlides(horizontalPhotos);

			logger.info(String.format("[INFO] : number of slides %d", slides.size()));

			slides.sort(Comparator.comparingInt(Slide::getScore));
			StringBuilder content = new StringBuilder();
			content.append(slides.size() + "\n");
			slides.forEach(slide -> content.append(slide.print() + "\n"));
			BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter(new File("out/" + fileName)));
			bufferedWriter.write(content.toString());

			bufferedWriter.close();

		} catch (FileNotFoundException e) {
			logger.error("[ERROR] : " + e.getMessage());
		} catch (IOException e) {
			logger.error("[ERROR] : " + e.getMessage());
		}
	}

	private void getHorizontalSlides(List<Photo> horizontalPhotos) {
		horizontalPhotos.forEach(photo -> slides.add(new Slide(photo, Optional.empty())));
	}

	private void getVerticalSlides(List<Photo> verticalPhotos) {
		List<List<Photo>> result = Lists.partition(new ArrayList<>(verticalPhotos), 2);
		for (List<Photo> photos : result) {
			if (photos.size() == 2) {
				this.slides.add(new Slide(photos.get(0), Optional.ofNullable(photos.get(1))));
			} else {
				this.slides.add(new Slide(photos.get(0), Optional.empty()));
			}
		}
	}
}
