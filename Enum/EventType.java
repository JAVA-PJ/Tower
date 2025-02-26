package Enum;

public enum EventType {
	PLANET_1("../Assets/c6.png"),
	PLANET_2("../Assets/c7.png"),
	CLOUD_1("../Assets/c1.png"),
	CLOUD_2("../Assets/c2.png"),
	CLOUD_3("../Assets/c3.png"),
	MOON_1("../Assets/c4.png"),
	STAR("../Assets/c5.png");
	

	private final String image;

	private EventType(String path) { this.image = path; }

	public String getPath() { return (image); }
}
