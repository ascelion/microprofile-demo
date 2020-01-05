package ascelion.kalah.gradle;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import groovy.util.Node;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.gradle.plugins.ide.eclipse.model.ClasspathEntry;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
public class EclipseFactorypathEntry implements ClasspathEntry {

	static private Optional<String> attribute(Node node, String name) {
		return Optional.ofNullable(node.attribute(name))
				.map(Object::toString);
	}

	private final String id;
	private final boolean enabled;
	private final boolean runInBatchMode;

	public EclipseFactorypathEntry(File file) {
		this(file.getAbsolutePath(), true, false);
	}

	public EclipseFactorypathEntry(Node child) {
		this.id = attribute(child, "id").orElse(null);
		this.enabled = attribute(child, "enabled").map(Boolean::valueOf).orElse(false);
		this.runInBatchMode = attribute(child, "runInBatchMode").map(Boolean::valueOf).orElse(false);
	}

	@Override
	public String getKind() {
		return "EXTJAR";
	}

	@Override
	public void appendNode(Node node) {
		final Map<String, Object> attributes = new LinkedHashMap<>();

		attributes.put("id", getId());
		attributes.put("enabled", isEnabled());
		attributes.put("runInBatchMode", isRunInBatchMode());
		attributes.put("kind", getKind());

		node.appendNode("factorypathentry", attributes);
	}
}
