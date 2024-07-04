package chatgptscam;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;

public class CatMeowing {
    public static void main(String[] args) {
        try {
            int sampleRate = 44100;
            int numChannels = 1;
            int sampleSizeInBits = 8;
            int duration = 1000;  // milliseconds
            int numSamples = sampleRate * duration / 1000;
            int frequency = 500;
            int amplitude = 128;

            AudioFormat audioFormat = new AudioFormat(sampleRate, sampleSizeInBits, numChannels, true, false);
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
            SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            sourceDataLine.open(audioFormat);
            sourceDataLine.start();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            for (int i = 0; i < numSamples; i++) {
                double time = (double) i / sampleRate;
                double hz = frequency + 100 * Math.sin(2 * Math.PI * 0.05 * time);
                out.write((int) (amplitude * Math.sin(2 * Math.PI * hz * time)));
            }

            sourceDataLine.write(out.toByteArray(), 0, out.size());
            sourceDataLine.drain();
            sourceDataLine.close();
        } catch (LineUnavailableException e) {
            System.out.println("Error generating sound: " + e.getMessage());
        }
    }
}
