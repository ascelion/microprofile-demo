package ascelion.kalah.shared.config;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.Collections.unmodifiableMap;
import static java.util.stream.Collectors.joining;

import org.eclipse.microprofile.config.spi.ConfigSource;

public class CommandLineSource implements ConfigSource {

	static private final String S_OPT = "-C";
	static private final String L_OPT = "--config";

	static private final Map<String, String> PROPERTIES = new HashMap<>();

	static public void setArguments(String[] arguments) {
		for (int k = 0; k < arguments.length; k++) {
			final String arg = arguments[k];

			if (arg.equals(S_OPT)) {
				if (k < arguments.length - 1) {
					addConfig(arguments[++k]);
				}
			} else if (arg.startsWith(S_OPT)) {
				addConfig(arg.substring(S_OPT.length()));
			} else if (arg.equals(L_OPT)) {
				if (k < arguments.length - 1) {
					addConfig(arguments[++k]);
				}
			}
		}
	}

	static void addConfig(final String arg) {
		final String[] vec = arg.split("=");

		if (vec.length > 1) {
			PROPERTIES.put(vec[0], stream(vec).skip(1).collect(joining("=")));
		} else {
			PROPERTIES.put(vec[0], "true");
		}
	}

	private Map<String, String> properties;

	@Override
	public Map<String, String> getProperties() {
		return unmodifiableMap(properties());
	}

	private Map<String, String> properties() {
		if (this.properties != null) {
			return this.properties;
		}

		synchronized (this) {
			if (this.properties != null) {
				return this.properties;
			}

			this.properties = new HashMap<>();

			final String cmd = System.getProperty("sun.java.command");

			if (PROPERTIES.isEmpty() && cmd != null) {
				setArguments(cmd.split("\\s"));
			}

			this.properties.putAll(PROPERTIES);

			return this.properties;
		}
	}

	@Override
	public String getValue(String propertyName) {
		return properties().get(propertyName);
	}

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public int getOrdinal() {
		return 10000;
	}
}
