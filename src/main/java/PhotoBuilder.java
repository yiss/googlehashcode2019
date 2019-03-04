import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.text.similarity.LevenshteinDistance;

public class PhotoBuilder {

	private Map<String, Integer> scoreTable;
	private AtomicInteger counter;
	private AtomicInteger tagId;

	PhotoBuilder() {
		this.scoreTable = new HashMap<>();
		this.counter = new AtomicInteger(0);
		this.tagId = new AtomicInteger(1);
	}

	public Photo build(String[] line) {
		Orientation orientation = Orientation.valueOf(line[0]);
		int tagSize = Integer.parseInt(line[1]);
		HashSet<String> tags = new HashSet<>(tagSize);
		int score = 0;
		for (int i=0; i<tagSize; i++) {
			String tag = line[i+2];
//			if (scoreTable.get(tag) == null) {
//				int count = tagId.getAndIncrement();
//				scoreTable.put(tag, count);
//				score += count;
//			} else {
//				score += scoreTable.get(tag);
//			}

			score += tag.hashCode()%tagSize;
			tags.add(tag);
		}

		return new Photo(counter.getAndIncrement(), orientation, tags, score);
	}

}
