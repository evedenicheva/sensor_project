package ekaterina.controllers;

import ekaterina.pojo.Device;
import ekaterina.pojo.Sensor;
import ekaterina.pojo.SensorInfo;
import ekaterina.service.DeviceService;
import ekaterina.service.MyUserService;
import ekaterina.service.SensorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/graphic")
public class GraphicController {

	private static Logger log = Logger.getLogger("GraphicController");

	@Autowired
	SensorInfoService sensorInfoService;

	@Autowired
	DeviceService deviceService;

	@Autowired
	MyUserService userService;

	@GetMapping("/{currentDeviceId}/{sensorName}")
	public String showAdminPageView(Model model, @PathVariable String sensorName,
	                                @PathVariable Long currentDeviceId) {
		List<SensorInfo> sensorInfos = sensorInfoService.
				findFewByDeviceId(15, sensorName, currentDeviceId);
		if (sensorInfos.size()<2) {
			//because repo will have one after creating a new
			List<SensorInfo> LatestSensorInfos = sensorInfoService
					.findLatestForEverySensorInCurrentDevice(currentDeviceId);
			model.addAttribute("sensorInfos", LatestSensorInfos);
			model.addAttribute("unsuccessful", "no info for this sensor");
			setAllStaticInfo(model, currentDeviceId);
			return "mainPage";
		}
		List<String> sensorInfosTime = new ArrayList<>();
		for (SensorInfo sensorInfo : sensorInfos) {
			if (Integer.toString(sensorInfo.getDate().getMinutes()).length()<2){
				sensorInfosTime.add(sensorInfo.getDate().getHours()+":0"+sensorInfo.getDate().getMinutes());
			}else
			sensorInfosTime.add(sensorInfo.getDate().getHours()+":"+sensorInfo.getDate().getMinutes());
		}
		model.addAttribute("sensorInfosTime", sensorInfosTime);
		model.addAttribute("deviceId", currentDeviceId);
		log.info("Added deviceId to model:"+currentDeviceId);
		model.addAttribute("sensorName", sensorName);
		model.addAttribute("sensorInfos", sensorInfos);
		return "graphic";
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
