package ekaterina.controllers.add;

import ekaterina.pojo.Device;
import ekaterina.pojo.MyUser;
import ekaterina.service.DeviceService;
import ekaterina.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/addUser")
public class AddUserController {

	@Autowired
	MyUserService myUserService;

	@Autowired
	DeviceService deviceService;

	@GetMapping
	public String showAddUserForm(Model model) {
		return "addUser";
	}

	@PostMapping
	public String submitAddUserForm(@ModelAttribute MyUser myUser,
	                                Device device,
	                                BindingResult result,
	                                Model model) {
		if (!myUserService.addUser(myUser) || result.hasErrors()) {
			model.addAttribute("unsuccessful", "Please check your information and try again.");
			return null;
		}
		deviceService.addDevice(device, myUser);
		return "login";
	}
}
