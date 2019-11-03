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

	@GetMapping
	public String showAdminPageView(Model model) {
		return "chooseDevice";
	}



}
