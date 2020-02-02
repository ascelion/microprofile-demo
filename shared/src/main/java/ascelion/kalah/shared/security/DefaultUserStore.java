//package ascelion.kalah.shared.security;
//
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//import java.util.Optional;
//
//import javax.enterprise.context.Dependent;
//
//import ascelion.config.api.ConfigPrefix;
//import ascelion.config.api.ConfigValue;
//
//import static java.util.Arrays.asList;
//
//import io.helidon.security.providers.httpauth.SecureUserStore;
//
//@Dependent
//@ConfigPrefix("security")
//class DefaultUserStore implements SecureUserStore {
//
//	@ConfigValue
//	static class UserInfo implements User {
//		@ConfigValue
//		String username;
//		@ConfigValue
//		String[] roles;
//
//		char[] password;
//
//		@ConfigValue
//		void setPassword(String password) {
//			this.password = password.toCharArray();
//		}
//
//		@Override
//		public String login() {
//			return this.username;
//		}
//
//		@Override
//		public boolean isPasswordValid(char[] password) {
//			return Arrays.equals(this.password, password);
//		}
//
//		@Override
//		public Collection<String> roles() {
//			return asList(this.roles);
//		}
//	}
//
//	@ConfigValue
//	private List<UserInfo> users;
//
//	@Override
//	public Optional<User> user(String login) {
//		return this.users.stream().filter(u -> u.username.equals(login))
//				.map(User.class::cast)
//				.findFirst();
//	}
//}
