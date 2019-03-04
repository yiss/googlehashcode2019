import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import lombok.Data;

@Data
public class Photo {
	private int id;
	private Orientation orientation;
	private Set<String> tags;
	private Integer score;

	public Photo(int id, Orientation orientation, Set<String> tags, Integer score) {
		this.id = id;
		this.orientation = orientation;
		this.tags = tags;
		this.score = score;
	}

	public int size() {
		return tags.size();
	}
}
