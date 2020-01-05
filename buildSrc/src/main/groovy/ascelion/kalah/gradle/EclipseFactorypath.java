package ascelion.kalah.gradle;

import java.util.ArrayList;
import java.util.List;

import groovy.util.Node;
import lombok.Getter;
import lombok.Setter;
import org.gradle.internal.xml.XmlTransformer;
import org.gradle.plugins.ide.internal.generator.XmlPersistableConfigurationObject;

@Getter
@Setter
public class EclipseFactorypath extends XmlPersistableConfigurationObject {
	private List<EclipseFactorypathEntry> entries = new ArrayList<>();

	public EclipseFactorypath(XmlTransformer xmlTransformer) {
		super(xmlTransformer);
	}

	@Override
	protected String getDefaultResourceName() {
		return "default.factorypath.xml";
	}

	@Override
	protected void load(Node xml) {
//		for (final Node child : getChildren(xml, "factorypathentry")) {
//			this.entries.add(new EclipseFactorypathEntry(child));
//		}
	}

	@Override
	protected void store(Node xml) {
		for (final Node child : getChildren(xml, "factorypathentry")) {
			xml.remove(child);
		}

		this.entries.stream().distinct().forEach(ent -> ent.appendNode(xml));
	}
}
