package ekaterina.controllers;

import ekaterina.pojo.Device;
import ekaterina.service.DeviceService;
import ekaterina.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/chooseDevice")
public class ChooseDeviceController {

	private static Logger log = Logger.getLogger("ChooseDeviceController");

//	@Autowired
//	MyUserService userService;
//
//	@Autowired
//	DeviceService deviceService;

	@GetMapping
	public String showAdminPageView(Model model) {
//		setAllUserDevices(model);
		return "chooseDevice";
	}

//	private void setAllUserDevices(Model model){
//		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User){
//			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			List<Device> devices = deviceService.findByUserId(userService.findByLogin(user.getUsername()).getId());
//			log.info("User devices:"+devices);
//			if (devices.size()>1) model.addAttribute("devices", devices);
//		}
//	}


}
