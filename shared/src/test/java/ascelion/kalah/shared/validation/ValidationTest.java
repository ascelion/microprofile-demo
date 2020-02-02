//package ascelion.kalah.shared.validation;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.junit.Assert.assertThat;
//
//import com.backbase.dbs.product.persistence.Arrangement;
//import com.backbase.dbs.product.test.UnicodeRandomizer;
//import java.nio.charset.Charset;
//import java.util.Set;
//import javax.persistence.Column;
//import javax.validation.ConstraintViolation;
//import javax.validation.Validator;
//import lombok.Builder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
//
//@RunWith(SpringRunner.class)
//@ActiveProfiles("test")
//@Import({
//    LocalValidatorFactoryBean.class,
//})
//@DataJpaTest(showSql = false)
//public class ValidationTest {
//
//    @Builder
//    @ColumnConstraint
//    static class Bean {
//        @AttributeConstraint(Arrangement.class)
//        private final String attribute1;
//        @AttributeConstraint(Arrangement.class)
//        private final String attribute2;
//        @AttributeConstraint(Arrangement.class)
//        private final String attribute3;
//
//        @Column(length = 20)
//        private final String value1;
//        @Column(length = 20)
//        private final String value2;
//        @Column(length = 20)
//        private final String value3;
//    }
//
//    @Autowired
//    private Validator val;
//
//    @Test
//    public void attributeContraint() {
//        final Set<ConstraintViolation<Bean>> vio =
//            this.val.validate(Bean.builder()
//                .attribute1("unknown1")
//                .attribute2("unknown2")
//                .attribute3("state")
//                .build());
//
//        vio.forEach(System.out::println);
//
//        assertThat(vio, hasSize(2));
//    }
//
//    @Test
//    public void columnConstraint() {
//        final UnicodeRandomizer gen = new UnicodeRandomizer(Charset.forName("UTF-8"), 80, 80);
//        final Bean ent = Bean.builder()
//            .value1(gen.getRandomValue())
//            .value2(gen.getRandomValue())
//            .value3(gen.getRandomValue())
//            .build();
//
//        final Set<ConstraintViolation<Bean>> vio = this.val.validate(ent);
//
//        vio.forEach(System.out::println);
//
//        assertThat(vio, hasSize(3));
//    }
//}
//
//
