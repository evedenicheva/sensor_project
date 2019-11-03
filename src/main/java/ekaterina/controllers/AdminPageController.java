package ekaterina.controllers;

import ekaterina.pojo.*;
import ekaterina.service.DeviceService;
import ekaterina.service.MyUserService;
import ekaterina.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/adminPage")
public class AdminPageController {

	private static Logger log = Logger.getLogger("AdminPageController");

	@Autowired
	MyUserService userService;

	@Autowired
	DeviceService deviceService;

	@Autowired
	SensorService sensorService;

	@GetMapping
	public String showAdminPageView(Model model) {
		fillAdminPageInfo(model);
		return "adminPage";
	}

	@RequestMapping(value = "/updateUser/{userId}", method = RequestMethod.POST,
			params = "update")
	public String updateUser(Model model, @ModelAttribute MyUser userFromForm,
	                         @PathVariable Long userId,
	                         @RequestParam(required = false, name = "role") List<String> roles) {
		log.info("Roles from form=" + roles);
		MyUser user = userService.findById(userId);
		user.setLogin(userFromForm.getLogin());
		user.setEmail(userFromForm.getEmail());
		addRoles(user, roles);
		model.addAttribute("result", "User " + user.getLogin() + " updated");
		if (userService.updateUserInfo(user)) log.info("User updated=" + user);
		fillAdminPageInfo(model);
		return "adminPage";
	}


	@RequestMapping(value = "/updateUser/{userId}", method = RequestMethod.POST,
			params = "delete")
	public String deleteUser(Model model, @ModelAttribute MyUser myUser,
	                         @PathVariable Long userId) {
		userService.deleteById(userId);
		fillAdminPageInfo(model);
		model.addAttribute("result", "User " + myUser.getLogin() + " deleted");
		return "adminPage";
	}

	@RequestMapping(value = "/updateDevice/{deviceId}", method = RequestMethod.POST,
			params = "update")
	public String updateDevice(Model model, @ModelAttribute Device deviceFromForm,
	                           @PathVariable Long deviceId, @RequestParam(required = false, name = "sensor") List<String> sensors) {
		log.info("Sensors from form:" + sensors);
		Device device = deviceService.findById(deviceId);
		device.setName(deviceFromForm.getName());
		device.setLocation(deviceFromForm.getLocation());
		device.setIpAddress(deviceFromForm.getIpAddress());
		List<Sensor> deviceSensors = sensorService.findByDeviceId(deviceId);
		if (sensors==null && deviceSensors.size()>=1) sensorService.deleteByDeviceId(deviceId);
		else {
			if (sensors!=null){
				if (deviceSensors.size() != sensors.size()) {
					List<String> sensorNames = deviceSensors.stream().map(Sensor::getName).collect(Collectors.toList());
					for (String sensor : sensors) {
						sensorNames.remove(sensor);
					}
					log.info("Sensors that was not checked:" + sensorNames);
					for (String sensorName : sensorNames) {
						sensorService.deleteByName(sensorName);
					}
				}
			}
		}
		deviceService.update(device);
		fillAdminPageInfo(model);
		model.addAttribute("result", "Device " + device.getName() + " updated");
		return "adminPage";
	}

	private void addRoles(MyUser user, List<String> roles) {
		List<MyRole> newRoles = new ArrayList<>();
		if (roles == null) {
			if (userService.findRoleByRoleType(RoleType.GUEST) == null) userService.addRoleByRoleType(RoleType.GUEST);
			newRoles.add(userService.findRoleByRoleType(RoleType.GUEST));
		} else {
			for (String role : roles) {
				if (role.equals("ADMIN")) {
					newRoles.add(userService.findRoleByRoleType(RoleType.ADMIN));
				}
				if (role.equals("USER")) {
					newRoles.add(userService.findRoleByRoleType(RoleType.USER));
				}
				if (role.equals("GUEST")) {
					newRoles.add(userService.findRoleByRoleType(RoleType.GUEST));
				}
			}
		}
		user.setMyRoles(new HashSet<>(newRoles));
		log.info("New roles added to User=" + user.getMyRoles());
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

		log.info("Users added:" + myUsers);
		log.info("Devices added:" + devices);
		log.info("Sensors added:" + sensors);
		log.info("Roles added:" + myRoles);
	}

}
