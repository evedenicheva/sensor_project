package ekaterina.repository;

import ekaterina.pojo.Device;
import ekaterina.service.SensorService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.logging.Logger;

@Repository
public class DeviceRepository {

	private static Logger log = Logger.getLogger("DeviceRepository");

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private SensorService sensorService;

	public List<Device> findAll(int count) {
		return sessionFactory.getCurrentSession()
				.createQuery("from Device", Device.class)
				.setMaxResults(count)
				.list();
	}

	public Device findById(Long id) {
		return sessionFactory.getCurrentSession()
				.get(Device.class, id);
	}

	public Device findByName(String name) {
		try {
			return sessionFactory.getCurrentSession()
					.createQuery("from Device where name like :param1", Device.class)
					.setParameter("param1", name)
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public boolean add(Device device) {
		sessionFactory.getCurrentSession().persist(device);
		return true;
	}

	public Long getMaxId() {
		return sessionFactory.getCurrentSession()
				.createQuery("select max(id) from Device", Long.class)
				.getSingleResult();
	}

	public boolean deleteById(Long id) {
		Device device = findById(id);
		if (device != null)
		sessionFactory.getCurrentSession().delete(device);
		return true;
	}

	public List<Device> findByUserId(Long id) {
		return sessionFactory.getCurrentSession().createQuery
				("from Device where myUser_id='"+id+"'", Device.class).list();
	}

	public List<Device> getAll() {
		return sessionFactory.getCurrentSession().createQuery("from Device",
				Device.class).list();
	}

	public void deleteByUserId(Long userId) {
		List<Device> userDevices = findByUserId(userId);
		for (Device userDevice : userDevices) {
			if (userDevice.getSensors().size()>0) sensorService.deleteByDeviceId(userDevice.getId());
		}
		for (Device userDevice : userDevices) {
			deleteById(userDevice.getId());
		}
	}

	public void update(Device device) {
		sessionFactory.getCurrentSession().update(device);
	}
}
