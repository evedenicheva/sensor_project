package ekaterina.controllers.delete;

import ekaterina.pojo.Device;
import ekaterina.pojo.Sensor;
import ekaterina.service.DeviceService;
import ekaterina.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/deleteDevice")
public class DeleteDeviceController {

	@Autowired
	DeviceService deviceService;

	@Autowired
	MyUserService userService;

	@GetMapping
	public String rejectDeleteDevice (Model model){
		setAllUserDevices(model);
		model.addAttribute("unsuccessful", "You should first choose device");
		return "mainPage";
	}

	@GetMapping("/{id}")
	public String showView(Model model, @PathVariable Long id) {
		setAllStaticInfo(model, id);
		if (!deviceService.deleteById(id)) model.addAttribute("unsuccessful", "Can not delete device. First delete all sensors");
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
