package ekaterina.controllers.delete;

import ekaterina.pojo.Device;
import ekaterina.pojo.Sensor;
import ekaterina.pojo.SensorInfo;
import ekaterina.service.DeviceService;
import ekaterina.service.MyUserService;
import ekaterina.service.SensorInfoService;
import ekaterina.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/deleteSensor")
public class DeleteSensorController {

	@Autowired
	DeviceService deviceService;

	@Autowired
	MyUserService userService;

	@Autowired
	SensorInfoService sensorInfoService;

	@GetMapping("/{sensorId}")
	public String deleteSensorView(Model model){
		setAllUserDevices(model);
		return "mainPage";
	}

	@Autowired
	SensorService sensorService;

	@PostMapping("/{sensorId}")
	public String deleteSensorButton(@PathVariable Long sensorId, @RequestParam Long deviceId, Model model){
		sensorInfoService.deleteByName(sensorService.findById(sensorId).getName());
		sensorService.deleteById(sensorId);
		setAllStaticInfo(model, deviceId);
		List<SensorInfo> sensorInfos = sensorInfoService
				.findLatestForEverySensorInCurrentDevice(deviceId);
		model.addAttribute("sensorInfos", sensorInfos);
		return "mainPage";
	}

	private void setAllStaticInfo(Model model, Long currentDeviceId){
		setAllUserDevices(model);
		setCurrentDevice(currentDeviceId, model);
		setAllSensors(currentDeviceId, model);
	}

	private void setAllUserDevices(Model model){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Device> devices = userService.findByLogin(user.getUsername()).getDevices();
		if (devices.size()>1) model.addAttribute("devices", devices);
	}

	private void setCurrentDevice(Long id, Model model){
		Device device = deviceService.findById(id);
		model.addAttribute("currentDevice", device);
	}

	private void setAllSensors(Long currentDeviceId, Model model){
		Device currentDevice = deviceService.findById(currentDeviceId);
		List<Sensor> sensors = currentDevice.getSensors();
		model.addAttribute("sensors", sensors);
	}

}
