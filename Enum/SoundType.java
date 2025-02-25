package Enum;

public enum SoundType {
	DROP("Assets/drop.wav"),
	GAMEOVER("Assets/gameover.wav"),
	BG_MUSIC("../Assets/BgMusic.wav"),
	CLICK("Assets/ClickSound.wav");

	private final String sound;

	private SoundType(String path) { this.sound = path; }

	public String getPath() { return (sound); }
}
