package ekaterina.controllers;

import ekaterina.pojo.Device;
import ekaterina.pojo.MyUser;
import ekaterina.pojo.Sensor;
import ekaterina.service.DeviceService;
import ekaterina.service.MyUserService;
import ekaterina.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/search")
public class SearchController {

	private static Logger log = Logger.getLogger("SearchController");

	@Autowired
	SensorService sensorService;

	@Autowired
	MyUserService userService;

	@Autowired
	DeviceService deviceService;

	@GetMapping
	public String search(@RequestParam("search-str") String str, Model model,
	                     @RequestParam Long currentDeviceId){
		if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User)){
			return "mainPage";
		}
		List<Sensor> sensors = sensorService.findByNameInAllDevices(str);
		log.info("Sensors from search" + sensors);
		if (sensors.size()<1) {
			model.addAttribute("unsuccessful", "Can not find any sensor");
			setAllStaticInfo(model, currentDeviceId);
			return "searchResult";
		}
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		MyUser currentUser = userService.findByLogin(user.getUsername());
		List<Device> devices = deviceService.findByUserId(currentUser.getId());
		log.info("Adding to searchResult model sensors:" + sensors);
		log.info("Adding to searchResult model devices:" + devices);
		model.addAttribute("sensorsFromSearch", sensors);
		model.addAttribute("devicesFromSearch", devices);
		setAllStaticInfo(model, currentDeviceId);
		return "searchResult";
	}

	private void setAllStaticInfo(Model model, Long currentDeviceId){
		setAllUserDevices(model);
		if (currentDeviceId!=null) {
			setCurrentDevice(currentDeviceId, model);
			setAllSensors(currentDeviceId, model);
		}
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
