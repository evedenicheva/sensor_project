package ekaterina.controllers;

import ekaterina.pojo.Device;
import ekaterina.pojo.Sensor;
import ekaterina.service.DeviceService;
import ekaterina.service.MyUserService;
import ekaterina.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

	@RequestMapping(method = RequestMethod.GET)
	public String mainPageView (Model model){
		setAllUserDevices(model);
		return "mainPage";
	}

	@GetMapping("/{id}")
	public String showCurrentDevice(@PathVariable Long id, Model model) {
		setAllUserDevices(model);
		Device device = setCurrentDevice(id, model);
		setAllSensors(device, model);
		return "mainPage";
	}

	@PostMapping("/{id}")
	public String SubmitAddSensorForm(@PathVariable Long id, @ModelAttribute Sensor sensor,
	                                  Model model, BindingResult result){
		setAllUserDevices(model);
		Device device = setCurrentDevice(id, model);
		if (device!=null) setAllSensors(device, model);
		if (sensorService.addSensor(sensor, device) && !result.hasErrors()) {
			return "mainPage";
		}
		else {
			model.addAttribute("unsuccessful", "Information is not correct. Please try again.");
			return "mainPage";
		}
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
		List<Sensor> sensors = device.getSensors();
		model.addAttribute("sensors", sensors);
		log.info("Added sensors to model: "+sensors);
		}
	}

}
