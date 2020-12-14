package uet.oop.bomberman.sounds;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

// To play sound using Clip, the process need to be alive.
// Hence, we use a Swing application.
public class GameSound extends JFrame {

   // Constructor
   public GameSound(String filePath, int LOOP) {

      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      try {
         // Open an audio input stream.
         URL url = this.getClass().getClassLoader().getResource(filePath);
         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
         // Get a sound clip resource.
         Clip clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioIn);
         clip.start();
         clip.loop(LOOP);
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }
}