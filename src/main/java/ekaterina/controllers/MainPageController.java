package ekaterina.controllers;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/mainPage")
public class MainPageController {

	private static Logger log = Logger.getLogger("MainPageController");

	@Autowired
	MyUserService userService;

	@Autowired
	DeviceService deviceService;

	@Autowired
	SensorService sensorService;

	@Autowired
	SensorInfoService sensorInfoService;

	@RequestMapping(method = RequestMethod.GET)
	public String mainPageView (Model model){
		setAllUserDevices(model);
		return "mainPage";
	}

	@GetMapping("/{deviceId}")
	public String showCurrentDevice(@PathVariable Long deviceId, Model model) {
		setAllUserDevices(model);
		Device device = setCurrentDevice(deviceId, model);
		List<SensorInfo> sensorInfos = sensorInfoService
				.findLatestForEverySensorInCurrentDevice(deviceId);
		model.addAttribute("sensorInfos", sensorInfos);
		setAllSensors(device, model);
		return "mainPage";
	}

	@PostMapping("/{currentDeviceId}")
	public String SubmitAddSensorForm(@PathVariable Long currentDeviceId, @ModelAttribute Sensor sensor,
	                                  Model model, BindingResult result){
		setAllUserDevices(model);
		Device device = setCurrentDevice(currentDeviceId, model);
			if (device!=null && sensorService.addSensor(sensor, device) && !result.hasErrors()) {
					SensorInfo sensorInfo = new SensorInfo();
					sensorInfo.setDate(new Date());
					sensorInfo.setDevice_id(currentDeviceId);
					sensorInfo.setName(sensor.getName());
					sensorInfo.setValue(sensor.getValue());
					log.info("Before adding sensorInfo=" + sensorInfo);
					sensorInfoService.addSensorInfo(sensorInfo);
					setAllSensors(device, model);
					List<SensorInfo> sensorInfos = sensorInfoService
						.findLatestForEverySensorInCurrentDevice(currentDeviceId);
					model.addAttribute("sensorInfos", sensorInfos);
					return "mainPage";
			}
		setAllSensors(device, model);
		List<SensorInfo> sensorInfos = sensorInfoService
				.findLatestForEverySensorInCurrentDevice(currentDeviceId);
		model.addAttribute("sensorInfos", sensorInfos);
			model.addAttribute("unsuccessful", "Information is not correct. Please try again.");
			return "mainPage";
	}

	@PostMapping
	public String RejectAddSensorForm(Model model){
		setAllUserDevices(model);
		model.addAttribute("unsuccessful", "You should first choose device");
		return "mainPage";
	}

	private void setAllUserDevices(Model model){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Device> devices = userService.findByLogin(user.getUsername()).getDevices();
		model.addAttribute("devices", devices);
	}

	private Device setCurrentDevice(Long id, Model model){
		Device device = deviceService.findById(id);
		if (device!=null) {
			model.addAttribute("currentDevice", device);
			return device;
		}
		return null;
	}

	private void setAllSensors(Device device, Model model){
		if (device!=null){
		List<Sensor> sensors = sensorService.findByDeviceId(device.getId());
		model.addAttribute("sensors", sensors);
		log.info("Added sensors to model: "+sensors);
		}
	}

}
