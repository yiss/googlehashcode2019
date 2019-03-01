import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Slide {
	private Photo first;
	private Optional<Photo> second;

	Set<String> getSlideTags() {
		Set<String> allTags = new HashSet<>();
		allTags.addAll(first.getTags());
		second.ifPresent(photo -> allTags.addAll(photo.getTags()));
		return allTags;
	}

}
