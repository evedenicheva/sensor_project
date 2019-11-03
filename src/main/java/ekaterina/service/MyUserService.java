package ekaterina.service;


import ekaterina.pojo.MyRole;
import ekaterina.pojo.MyUser;
import ekaterina.repository.MyRoleRepository;
import ekaterina.repository.MyUserRepository;
import ekaterina.pojo.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class MyUserService {

	@Autowired
	MyUserRepository myUserRepository;

	@Autowired
	MyRoleRepository myRoleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Transactional
	public MyUser findById(Long id){
		return myUserRepository.findById(id);
	}

	@Transactional
	public MyUser findByLogin(String login){
		return myUserRepository.findByLogin(login);
	}

	@Transactional
	public boolean addUser(MyUser myUser){
		if (myUser.getLogin().isEmpty() || myUser.getPassword().isEmpty() ||
		myUser.getEmail().isEmpty() || myUserRepository.findByLogin(myUser.getLogin())!= null) return false;
		MyRole userRole = myRoleRepository.findByRoleType(RoleType.USER);
		if(myUser.getLogin().equals("admin_admin")){
			userRole = new MyRole();
			userRole.setRoleType(RoleType.ADMIN);
			myRoleRepository.add(userRole);
		}
		if (userRole == null){
			userRole = new MyRole();
			userRole.setRoleType(RoleType.USER);
			myRoleRepository.add(userRole);
		}
		String encodedPassword = passwordEncoder.encode(myUser.getPassword());
		myUser.setPassword(encodedPassword);
		myUser.setMyRoles(Set.of(userRole));
		return myUserRepository.add(myUser);
	}

	@Transactional
	public MyRole findRoleByRoleType(RoleType roleType){
		return myRoleRepository.findByRoleType(roleType);
	}

	@Transactional
	public void deleteById(Long userId) {
		myUserRepository.deleteById(userId);
	}

	@Transactional
	public List<MyUser> getAll() {
		return myUserRepository.getAll();
	}

	@Transactional
	public List<MyRole> getAllRoles() {
		return myRoleRepository.findAll();
	}

	@Transactional
	public boolean updateUserInfo(MyUser myUser){
		return myUserRepository.updateUserInfo(myUser);
	}

	@Transactional
	public void addRoleByRoleType(RoleType roleType) {
		MyRole myRole = new MyRole();
		myRole.setRoleType(roleType);
		myRoleRepository.add(myRole);
	}
}
