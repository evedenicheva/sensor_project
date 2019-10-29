package ekaterina.repository;

import ekaterina.pojo.MyRole;
import ekaterina.pojo.RoleType;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class MyRoleRepository {

	@Autowired
	SessionFactory sessionFactory;

	public boolean add(MyRole myRole){
		sessionFactory.getCurrentSession().save(myRole);
		return true;
	}

	public MyRole findByRoleType(RoleType roleType){
		try {
			return sessionFactory.getCurrentSession()
					.createQuery("from MyRole where roleType like :param", MyRole.class)
					.setParameter("param", roleType)
					.getSingleResult();
		} catch(Exception e){
			return null;
		}
	}

	public List<MyRole> findAll() {
		return sessionFactory.getCurrentSession().createQuery("from MyRole", MyRole.class).list();
	}
}
