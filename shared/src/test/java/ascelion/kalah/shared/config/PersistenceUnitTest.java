//package ascelion.kalah.shared.config;
//
//import javax.inject.Inject;
//import javax.persistence.EntityManager;
//
//import static org.hamcrest.Matchers.notNullValue;
//import static org.junit.Assert.assertThat;
//
//import org.jboss.weld.junit4.WeldInitiator;
//import org.junit.Rule;
//import org.junit.Test;
//
//public class PersistenceUnitTest {
//
//	@Rule
//	public WeldInitiator weld = WeldInitiator
//			.from(DataSourceBean.class, PersistenceBean.class, PersistenceUnitInfoBean.class,
//					ascelion.microprofile.config.cdi.ConfigExtension.class,
////					org.apache.geronimo.config.cdi.ConfigExtension.class
//					io.smallrye.config.inject.ConfigExtension.class)
//			.inject(this).build();
//
//	@Inject
//	private EntityManager em;
//
//	@Test
//	public void run() {
//		assertThat(this.em, notNullValue());
//	}
//
//}
