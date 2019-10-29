package ekaterina.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class MyRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private RoleType roleType;

	public MyRole(RoleType roleType) {
		this.roleType = roleType;
	}
}
