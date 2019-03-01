import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class Photo {
	private int id;
	private Orientation orientation;
	private Set<String> tags;

	public Photo(int id, String[] line) {
		this.id = id;
		this.orientation = Orientation.valueOf(line[0]);
		int tagSize = Integer.parseInt(line[1]);
		this.tags = new HashSet<>(tagSize);
		for (int i=0; i<tagSize; i++) {
			this.tags.add(line[i+2]);
		}
	}
}
