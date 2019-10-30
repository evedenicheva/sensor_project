package ekaterina.repository;

import ekaterina.pojo.SensorInfo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.logging.Logger;

@Repository
public class SensorInfoRepository {

	private static Logger log = Logger.getLogger("SensorInfoRepository");

	@Autowired
	SessionFactory sessionFactory;

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
}
