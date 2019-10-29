package ekaterina.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class MyUser implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String login;

	private String password;

	private String email;

	@OneToMany(mappedBy = "myUser", fetch = FetchType.EAGER)
	private List <Device> devices;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<MyRole> myRoles;

}
