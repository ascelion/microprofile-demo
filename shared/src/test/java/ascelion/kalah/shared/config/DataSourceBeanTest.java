package ascelion.kalah.shared.config;

import javax.inject.Inject;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.jboss.weld.junit4.WeldInitiator;
import org.junit.Rule;
import org.junit.Test;

public class DataSourceBeanTest {

	@Rule
	public WeldInitiator weld = WeldInitiator
			.from(DataSourceBean.class,
					ascelion.microprofile.config.cdi.ConfigExtension.class,
					io.smallrye.config.inject.ConfigExtension.class)
			.inject(this)
			.build();

	@Inject
	private DataSourceBean dsb;

	@Test
	public void run() {
		assertThat(this.dsb.datasource(), notNullValue());
	}

}
