package ekaterina.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = "device_seq")
public class Device implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "device_seq")
	private Long id;

	private String ipAddress;

	private String name;

	private String location;

	@OneToMany(mappedBy = "device", fetch = FetchType.EAGER)
	private List<Sensor> sensors;

	@ManyToOne
	private MyUser myUser;

	public Device() {
	}

	public Device(Long id, String ipAddress, String name, String location, List<Sensor> sensors, MyUser myUser) {
		this.id = id;
		this.ipAddress = ipAddress;
		this.name = name;
		this.location = location;
		this.sensors = sensors;
		this.myUser = myUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

	public MyUser getMyUser() {
		return myUser;
	}

	public void setMyUser(MyUser myUser) {
		this.myUser = myUser;
	}


	@Override
	public String toString() {
		return "Device{" +
				"name='" + name + '\'' +
				'}';
	}
}
