package ekaterina.service;

import ekaterina.pojo.Device;
import ekaterina.pojo.Sensor;
import ekaterina.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@Service
public class SensorService {

	private static Logger log = Logger.getLogger("SensorService");

	@Autowired
	SensorRepository sensorRepository;

	@Transactional
	public Sensor findById(Long id){
		return sensorRepository.findById(id);
	}


	@Transactional
	public boolean addSensor(Sensor sensor, Device device){
		sensor.setDevice(device);
		log.info("Adding sensor:"+sensor);
		if (sensor.getName().isEmpty() || sensor.getDevice() == null ||
				sensor.getValue().isEmpty()  || sensorRepository.findByNameInCurrentDevice(sensor.getName(), device.getId())!= null) return false;
		else return sensorRepository.add(sensor);
	}

	@Transactional
	public void deleteById(Long sensorId) {
		sensorRepository.deleteById(sensorId);
	}

	@Transactional
	public List<Sensor> findByNameInAllDevices(String str) {
		return sensorRepository.findByNameInAllUserDevices(str);
	}

	@Transactional
	public List<Sensor> getAll() {
		return sensorRepository.getAll();
	}

	@Transactional
	public void deleteByDeviceId(Long id) {
		sensorRepository.deleteByDeviceId(id);
	}

	@Transactional
	public List<Sensor> findByDeviceId(Long deviceId) {
		return sensorRepository.findByDeviceId(deviceId);
	}

	@Transactional
	public void deleteByName(String sensorName) {
		sensorRepository.deleteByName(sensorName);
	}
}
