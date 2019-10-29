package ekaterina.pojo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@SequenceGenerator(name = "sensor_seq")
public class Sensor implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sensor_seq")
	private Long id;

	private String name;

	private String value;

	@ManyToOne
	private Device device;

	public Sensor() {
	}

	public Sensor(Long id, String name, String value, Device device) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.device = device;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	@Override
	public String toString() {
		return "Sensor{" +
				"id=" + id +
				", name='" + name + '\'' +
				", value='" + value + '\'' +
				", device=" + device +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Sensor sensor = (Sensor) o;

		if (id != null ? !id.equals(sensor.id) : sensor.id != null) return false;
		if (name != null ? !name.equals(sensor.name) : sensor.name != null) return false;
		if (value != null ? !value.equals(sensor.value) : sensor.value != null) return false;
		return device != null ? device.equals(sensor.device) : sensor.device == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (value != null ? value.hashCode() : 0);
		result = 31 * result + (device != null ? device.hashCode() : 0);
		return result;
	}
}
