import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Slide {
	private Photo first;
	private Optional<Photo> second;

	public Integer getScore() {
		if (second.isPresent()) {
			return first.getScore() + second.get().getScore();
		} else {
			return first.getScore();
		}
	}

	public String print() {
		if (second.isPresent()) {
			return first.getId() + " " + second.get().getId();
		} else {
			return "" + first.getId();
		}
	}
}
