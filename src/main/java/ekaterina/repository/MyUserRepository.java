package ekaterina.repository;

import ekaterina.pojo.MyRole;
import ekaterina.pojo.MyUser;
import ekaterina.service.DeviceService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.logging.Logger;

@Repository
public class MyUserRepository {

	private static Logger log = Logger.getLogger("MyUserRepository");

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private DeviceService deviceService;

	public List<MyUser> findAll(int count) {
		return sessionFactory.getCurrentSession()
				.createQuery("from MyUser", MyUser.class)
				.setMaxResults(count)
				.list();
	}

	public MyUser findById(Long id) {
		return sessionFactory.getCurrentSession()
				.get(MyUser.class, id);
	}

	public MyUser findByLogin(String login) {
			List<MyUser> myUsers = sessionFactory.getCurrentSession()
					.createQuery("from MyUser where login='" + login + "'",
							MyUser.class).list();
			if (myUsers.size()==1) return myUsers.get(0);
			else {
				log.info("FOUND MORE USERS WITH SIMILAR LOGIN: "+myUsers);
				return null;
			}
	}

	public boolean add(MyUser myUser) {
		sessionFactory.getCurrentSession().save(myUser);
		return true;
	}

	public void deleteById(Long userId) {
		MyUser myUser = findById(userId);
		if (myUser.getDevices().size()>0) deviceService.deleteByUserID(userId);
		log.info("Deleting User="+myUser);
		sessionFactory.getCurrentSession().delete(myUser);
	}

	public List<MyUser> getAll() {
		return sessionFactory.getCurrentSession().createQuery("from MyUser", MyUser.class).list();
	}

	public boolean updateUserInfo(MyUser myUser) {
		sessionFactory.getCurrentSession().update(myUser);
		return true;
	}

}
