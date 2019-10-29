package ekaterina.controllers.add;

import ekaterina.pojo.Device;
import ekaterina.pojo.MyUser;
import ekaterina.pojo.Sensor;
import ekaterina.service.DeviceService;
import ekaterina.service.MyUserService;
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
@RequestMapping("/addDevice")
public class AddDeviceController {

	private static Logger log = Logger.getLogger("AddDeviceController");

	@Autowired
	DeviceService deviceService;

	@Autowired
	MyUserService userService;

	@GetMapping
	public String addDeviceView (Model model){
		setAllUserDevices(model);
		return "addDevice";
	}


	@PostMapping
	public String submitAddDeviceForm(@ModelAttribute Device device,
	                                  BindingResult result,
	                                  Model model){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		log.info("Username=" + user.getUsername());
		MyUser currentUser = userService.findByLogin(user.getUsername());
		if (deviceService.addDevice(device, currentUser) && !result.hasErrors()) {
			setAllStaticInfo(model, device.getId());
			log.info("added device:"+ device);
			return "mainPage";
		}
		else {
			model.addAttribute("unsuccessful", "Information is not correct");
			return null;
		}
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
