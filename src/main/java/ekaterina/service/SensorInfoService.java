package ekaterina.service;

import ekaterina.pojo.SensorInfo;
import ekaterina.repository.SensorInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SensorInfoService {

	@Autowired
	SensorInfoRepository sensorInfoRepository;

	@Transactional
	public List<SensorInfo> findFewByDeviceId(int count, String name, Long deviceId){
		return sensorInfoRepository.findFewByDeviceId(count, name, deviceId);
	}

	@Transactional
	public void addSensorInfo(SensorInfo sensorInfo) {
		sensorInfoRepository.add(sensorInfo);
	}

	@Transactional
	public List<SensorInfo> findLatestForEverySensorInCurrentDevice(Long deviceId) {
		return sensorInfoRepository.findLatestForEverySensorInCurrentDevice(deviceId);
	}

	@Transactional
	public void deleteByName(String name) {
		sensorInfoRepository.deleteByName(name);
	}
}
