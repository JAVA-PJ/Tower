package Enum;

public enum SoundType {
	DROP("Assets/drop.wav"),
	FALL("Assets/rotate.wav"),
	CLICK("Assets/ClickSound.wav"),
	GAMEOVER("Assets/gameover.wav"),
	SOUND_MENU("Assets/BgMusic.wav"),
	SOUND_GAME("Assets/SoundGame.wav");

	private final String sound;

	private SoundType(String path) { this.sound = path; }

	public String getPath() { return (sound); }
}
