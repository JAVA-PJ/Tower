package Enum;

public enum ImageType {
	EXIT_BUTTON("../Assets/exit.png"),
	SOUND_ON("../Assets/sound_On.png"),
	SOUND_OFF("../Assets/sound_Off.png"),
	REPLAY_BUTTON("../Assets/replay.png"),
	BACK_BUTTON("../Assets/ButtonBack.png"),
	START_BUTTON("../Assets/StartButton.png"),
	EXIT_BUTTON_MENU("../Assets/ExitButton.png"),
	HOWTOPLAY_BUTTON("../Assets/HowToPlayButton.png"),

	SUN("../Assets/Sun.gif"),
	BLOCK("../Assets/block.png"),
	SCORE("../Assets/score.png"),
	SPACEBAR("../Assets/spacebar.png"),
	NO_HEALTH("../Assets/no-health.png"),
	FULL_HEALTH("../Assets/full-health.png"),


	BG_GAME("../Assets/background.png"),
	BG_GAMEOVER("../Assets/gameover.png"),
	BG_MENU("../Assets/MenuBackground.png"),
	BG_HOWTOPLAY("../Assets/HowToPlay.png");
	

	private final String image;

	private ImageType(String path) { this.image = path; }

	public String getPath() { return (image); }
}
