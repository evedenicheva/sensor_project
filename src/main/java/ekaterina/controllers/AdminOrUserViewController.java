package ekaterina.controllers;
import ekaterina.pojo.Device;
import ekaterina.pojo.MyRole;
import ekaterina.pojo.MyUser;
import ekaterina.pojo.Sensor;
import ekaterina.repository.MyUserRepository;
import ekaterina.service.DeviceService;
import ekaterina.service.MyUserService;
import ekaterina.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.desktop.UserSessionEvent;
import java.util.List;

@Controller
@RequestMapping("/")
public class AdminOrUserViewController {

	@Autowired
	MyUserService userService;

	@Autowired
	DeviceService deviceService;

	@Autowired
	SensorService sensorService;

	@GetMapping
	public String showViewBasedOnRoleType(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
			fillAdminPageInfo(model);
			return "adminPage";
		}
		else if(authentication.getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals("ROLE_USER"))) {
			setAllUserDevices(model);
			return "mainPage";
		}
		else return "login";
	}

	private void setAllStaticInfo(Model model, Long currentDeviceId){
		setAllUserDevices(model);
		setCurrentDevice(currentDeviceId, model);
		setAllSensors(currentDeviceId, model);
	}

	private void setAllUserDevices(Model model){
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User){
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<Device> devices = userService.findByLogin(user.getUsername()).getDevices();
			if (devices.size()>1) model.addAttribute("devices", devices);
		}
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

	private void fillAdminPageInfo(Model model) {
		List<MyUser> myUsers = userService.getAll();
		List<Device> devices = deviceService.getAll();
		List<Sensor> sensors = sensorService.getAll();
		List<MyRole> myRoles = userService.getAllRoles();

		model.addAttribute("myUsers", myUsers);
		model.addAttribute("devices", devices);
		model.addAttribute("sensors", sensors);
		model.addAttribute("myRoles", myRoles);
	}


}
