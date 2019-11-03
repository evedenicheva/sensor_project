package ekaterina.service;

import ekaterina.pojo.Device;
import ekaterina.pojo.MyRole;
import ekaterina.pojo.MyUser;
import ekaterina.pojo.RoleType;
import ekaterina.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@Service
public class DeviceService {

	private static Logger log = Logger.getLogger("DeviceService");

	@Autowired
	DeviceRepository deviceRepository;

	@Transactional
	public Device findById(Long id){
		return deviceRepository.findById(id);
	}


	@Transactional
	public boolean addDevice(Device device, MyUser myUser){
		MyRole adminRole = new MyRole();
		adminRole.setRoleType(RoleType.ADMIN);
		Device deviceWithSameName = deviceRepository.findByName(device.getName());
		log.info("DeviceFromRepo:"+deviceWithSameName);
		if ( device.getName().isEmpty() ||
				device.getIpAddress().isEmpty()|| device.getLocation().isEmpty() ||
				myUser.getMyRoles().contains(adminRole)) return false;
		device.setMyUser(myUser);
		return deviceRepository.add(device);
	}


	@Transactional
	public boolean deleteById(Long id) {
		Device device = deviceRepository.findById(id);
		log.info("Delete by id Device: "+device);
		if (device.getSensors().size()>0) return false;
		return deviceRepository.deleteById(id);
	}

	@Transactional
	public List<Device> findByUserId(Long id) {
		return deviceRepository.findByUserId(id);
	}

	@Transactional
	public List<Device> getAll() {
		return deviceRepository.getAll();
	}

	@Transactional
	public void deleteByUserID(Long userId) {
		deviceRepository.deleteByUserId(userId);
	}

	@Transactional
	public void update(Device device) {
		deviceRepository.update(device);
	}
}
