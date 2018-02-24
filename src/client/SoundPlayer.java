package client;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Class for creating, storing and playing all sounds in the game.
 * 
 * @author Tom Bjurenlind, Jan Rasmussen, Christer Sonesson, Emir Zivcic
 * @version 1.0
 */
public class SoundPlayer {
	// Create static instances of this class, each providing functionality to
	// play a single audio.
	public static SoundPlayer attackEnemy = new SoundPlayer("res/sounds/attack.wav", false);
	public static SoundPlayer goRoom = new SoundPlayer("res/sounds/go.wav", false);
	public static SoundPlayer useItem = new SoundPlayer("res/sounds/eat.wav", false);
	public static SoundPlayer pickItem = new SoundPlayer("res/sounds/pick.wav", false);
	public static SoundPlayer dropItem = new SoundPlayer("res/sounds/drop.wav", false);
	public static SoundPlayer background = new SoundPlayer("res/sounds/background.wav", true);

	// Stores and provides functionality to play the stored audio stream.
	private Clip audioClip;
	// Indicates whether to continue to play continuously or not.
	private boolean playContinuously;

	/**
	 * Constructor for new SoundPlayer object.
	 * 
	 * @param filePath
	 *            The relative path to the audio file.
	 * @return A new SoundPlayer instance for playing the new audio.
	 */
	private SoundPlayer(String filePath, boolean playContinuously) {
		this.playContinuously = playContinuously;

		try {
			// Load audio.
			AudioInputStream audio = AudioSystem.getAudioInputStream(SoundPlayer.
					class.getClassLoader().getResource(filePath));
			// Store audio in clip field of this SoundPlayer object.
			this.audioClip = AudioSystem.getClip();
			this.audioClip.open(audio);
		} catch (UnsupportedAudioFileException | IOException e) {
			System.out.println(e.getMessage());
		} catch (LineUnavailableException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Play the audio-clip managed by this particular SoundPlayer.
	 */
	public synchronized void playAudio() {
		// Error handling
		if (audioClip == null)
			return;

		// Play continuously?
		if (playContinuously)
			setShouldPlayContinuously(true);

		new Thread() { // Run on separate thread than the calling class's thread
						// (GUI).
			public void run() {
				do {
					// Stops the audio-clip and restarts it, or simply
					// starts it, if it's continuous wait for it to finish.
					if (!audioClip.isActive() || !getShouldPlayContinuously()) {
						audioClip.stop();
						audioClip.setFramePosition(0);
						audioClip.start();
					}
				} while (getShouldPlayContinuously()); // Play again?
			}
		}.start(); // Start playing audio.
	}

	/**
	 * Stops this SoundPlayer from playing its audio continuously.
	 */
	public synchronized void stopPlaying() {
		setShouldPlayContinuously(false);
	}

	/**
	 * Determines whether this player is playing its audio continuously.
	 * 
	 * @return Whether this SoundPlayer's audio is currently playing
	 *         continuously.
	 */
	private synchronized boolean getShouldPlayContinuously() {
		return playContinuously;
	}

	/**
	 * Sets whether to play this SoundPlayer's audio continuously or not.
	 * 
	 * @param playOn
	 *            Whether to play continuously.
	 */
	private synchronized void setShouldPlayContinuously(boolean playOn) {
		this.playContinuously = playOn;
	}
}
