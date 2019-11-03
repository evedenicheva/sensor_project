package ekaterina.repository;

import ekaterina.pojo.Device;
import ekaterina.pojo.MyUser;
import ekaterina.pojo.Sensor;
import ekaterina.service.DeviceService;
import ekaterina.service.MyUserService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class SensorRepository {

	private static Logger log = Logger.getLogger("SensorRepository");

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	MyUserService userService;

	@Autowired
	DeviceService deviceService;

	public Sensor findById(Long id) {
		return sessionFactory.getCurrentSession()
				.get(Sensor.class, id);
	}
	public List<Sensor> findByDeviceId(Long deviceId) {
		return sessionFactory.getCurrentSession().createQuery("from Sensor where device_id="+deviceId, Sensor.class).list();
	}

	public Sensor findByNameInCurrentDevice(String name, Long deviceId) {
		List<Sensor> sensorsWithSameNames = sessionFactory.getCurrentSession()
				.createQuery("from Sensor where name='" + name +
						"' and device_id='"+deviceId+"'", Sensor.class).list();
		if (sensorsWithSameNames.size()==1) return sensorsWithSameNames.get(0);
		else return null;

	}

	public boolean add(Sensor sensor) {
		sessionFactory.getCurrentSession().persist(sensor);
		return true;
	}

	public void deleteById(Long sensorId) {
		Sensor sensor = findById(sensorId);
		if (sensor!=null) sessionFactory.getCurrentSession().delete(sensor);
	}

	public List<Sensor> findByNameInAllUserDevices(String str) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		MyUser currentUser = userService.findByLogin(user.getUsername());
		List <Device> devices = deviceService.findByUserId(currentUser.getId());
		List<Sensor> sensorsFromDevices = new ArrayList<>();
		for (Device device : devices) {
			List<Sensor> sensors = sessionFactory.getCurrentSession().createQuery
					("from Sensor where name='" +str+
							"' and device_id='" + device.getId() + "'", Sensor.class).list();
			if (sensors!=null) sensorsFromDevices.addAll(sensors);
		}
		log.info("sensors from User devices with name="+str+" "+sensorsFromDevices);
		return sensorsFromDevices;
	}

	public List<Sensor> getAll() {
		return sessionFactory.getCurrentSession().createQuery("from Sensor", Sensor.class).list();
	}

	public void deleteByDeviceId(Long id) {
		List<Sensor> deviceSensors = findByDeviceId(id);
		for (Sensor deviceSensor : deviceSensors) {
			deleteById(deviceSensor.getId());
		}
	}

	public void deleteByName(String sensorName) {
		List<Sensor> devicesSensors = findByName(sensorName);
		for (Sensor devicesSensor : devicesSensors) {
			sessionFactory.getCurrentSession().delete(devicesSensor);
		}
	}

	private List<Sensor> findByName(String sensorName) {
		return sessionFactory.getCurrentSession().createQuery("from Sensor where name='"+sensorName+"'",
				Sensor.class).list();
	}
}
