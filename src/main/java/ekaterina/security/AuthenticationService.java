package ekaterina.security;

import ekaterina.pojo.MyUser;
import ekaterina.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service("authService")
public class AuthenticationService implements UserDetailsService {

	@Autowired
	private MyUserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String login) {
		MyUser myUser = userRepository.findByLogin(login);
		if (myUser == null) {
			throw new UsernameNotFoundException(login);
		}
		User user = new User(myUser.getLogin(), myUser.getPassword(),
				myUser.getMyRoles().stream().map(myRole -> new SimpleGrantedAuthority("ROLE_" + myRole.getRoleType()))
				.collect(Collectors.toSet()));
		return user;
	}



}
