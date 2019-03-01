import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SlideShow {

	private List<Slide> slides;
	private Set<String> allTags;
	private HashMap<String, Set<Slide>> groups;
	private HashMap<String, Integer> hotTags;

	public SlideShow() {
		this.slides = new ArrayList<>();
		this.allTags = new HashSet<>();
		this.groups = new HashMap<>();
		this.hotTags = new HashMap<>();
	}

	public void parse(String fileName) {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)));
			String line = bufferedReader.readLine();
			int counter = 0;
			List<Photo> verticalPhotos = new ArrayList<>();
			List<Photo> horizontalPhotos = new ArrayList<>();
			while ((line = bufferedReader.readLine()) != null) {
				Photo photo = new Photo(counter, line.split(" "));
				allTags.addAll(photo.getTags());
				if (photo.getOrientation() == Orientation.V) {
					verticalPhotos.add(photo);
				} else {
					horizontalPhotos.add(photo);
				}
				counter++;
			}

			List<Slide> horizontalPhotosSlides = new ArrayList<>(horizontalPhotos.stream()
				.map(photo -> new Slide(photo, Optional.empty())).collect(Collectors.toList()));

			List<Slide> verticalPhotosSlides = new ArrayList<>();

			if (verticalPhotos.size()%2 == 0) {
				for (int i =0; i<verticalPhotos.size(); i+=2) {
					verticalPhotosSlides.add(new Slide(verticalPhotos.get(i), Optional.of(verticalPhotos.get(i+1))));
				}
			} else {
				for (int i =0; i<verticalPhotos.size()-1; i+=2) {
					verticalPhotosSlides.add(new Slide(verticalPhotos.get(i), Optional.of(verticalPhotos.get(i+1))));
				}
				verticalPhotosSlides.add(new Slide(verticalPhotos.get(verticalPhotos.size()-1), Optional.empty()));
			}

			slides.addAll(verticalPhotosSlides);
			slides.addAll(horizontalPhotosSlides);
//
//			for (Slide s : slides) {
//				int score = calculateScore(s);
//				if (scores.get(score) != null) {
//					scores.get(score).add(s);
//				} else {
//					List<Slide> scoredSlides = new ArrayList<>();
//					scoredSlides.add(s);
//					scores.put(score, scoredSlides);
//				}
//			}

//			for (String tag : allTags) {
//				if (hotTags.get(tag) == null) {
//					hotTags.put(tag, 0);
//				}
//				for (Slide s : slides) {
//					 if (s.getSlideTags().contains(tag)) {
//						hotTags.put(tag, hotTags.get(tag) + 1);
//					}
//				}
//			}
//
//			System.out.println(Collections.singletonList(hotTags));

//			for (Slide s : slides) {
//				for (String tag : s.getSlideTags()) {
//					if (groups.get(tag) == null) {
//						HashSet<Slide> group = new HashSet<>();
//						group.add(s);
//						groups.put(tag, group);
//					} else {
//						groups.get(tag).add(s);
//					}
//				}
//			}
//
//			StringBuilder content = new StringBuilder();
//			content.append(slides.size() + "\n");
//
//			for (Set<Slide> slides : groups.values()) {
//				for (Slide s : slides) {
//					if (s.getSecond().isPresent()) {
//						content.append(s.getFirst().getId() + " " + s.getSecond().get().getId() + "\n");
//					} else {
//						content.append(s.getFirst().getId() + "\n");
//					}
//				}
//			}
//
//			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("out_"+fileName)));
//			bufferedWriter.write(content.toString());
//			bufferedWriter.close();
//			ScoreCaluclator.calculateScore(slides, "out_"+fileName);

			Collections.sort(slides,
				Comparator.comparingInt(o -> o.getSlideTags().size()));


			for (Slide s : slides) {
				System.out.println(s);
			}


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	public int calculateScore(Slide s) {
		return Sets.difference(allTags, s.getSlideTags()).size();
	}


}
