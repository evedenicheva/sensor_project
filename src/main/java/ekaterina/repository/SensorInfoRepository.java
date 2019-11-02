package ekaterina.repository;

import ekaterina.pojo.Sensor;
import ekaterina.pojo.SensorInfo;
import ekaterina.service.SensorService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class SensorInfoRepository {

	private static Logger log = Logger.getLogger("SensorInfoRepository");

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	SensorService sensorService;

	public List<SensorInfo> findFewByDeviceId(int count, String name, Long deviceId) {
		return sessionFactory.getCurrentSession()
				.createQuery("from sensor_info where name='" + name + "' and device_id=" + deviceId + " order by date desc", SensorInfo.class)
				.setMaxResults(count)
				.list();
	}


	public void add(SensorInfo sensorInfo) {
		log.info("Before saving SensorInfo:" + sensorInfo);
		sessionFactory.getCurrentSession().save(sensorInfo);
	}

	public List<SensorInfo> findLatestForEverySensorInCurrentDevice(Long deviceId) {
		List<Sensor> deviceSensors = sensorService.findByDeviceId(deviceId);
		List<String> sensorsNames = new ArrayList<>();
		for (Sensor deviceSensor : deviceSensors) {
			sensorsNames.add(deviceSensor.getName());
		}
		List<SensorInfo> latestForEverySensor = new ArrayList<>();
		for (String sensorName : sensorsNames) {
			latestForEverySensor.addAll(sessionFactory.getCurrentSession().createQuery("from sensor_info where name='" + sensorName + "' and device_id=" + deviceId + " order by date desc", SensorInfo.class)
					.setMaxResults(1).list());
		}
		return latestForEverySensor;
	}

	public void deleteByName(String name) {
		List<SensorInfo> sensorInfos = findByName(name);
		for (SensorInfo sensorInfo : sensorInfos) {
			sessionFactory.getCurrentSession().delete(sensorInfo);
		}
	}

	private List<SensorInfo> findByName(String name) {
		return sessionFactory.getCurrentSession().createQuery("from sensor_info where name='" + name+"'", SensorInfo.class).list();
	}
}
