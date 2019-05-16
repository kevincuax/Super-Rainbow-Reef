import javax.sound.sampled.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class MusicPlayer implements Runnable {

//    private ArrayList<String> musicFiles;
    private static HashMap <String, String> musicFiles;
    private int currentSongIndex;
    public MusicPlayer(String... files){
//        musicFiles = new ArrayList<>();
//        public static HashMap<String,Image> sprites;
        musicFiles = new HashMap<>();

        for(String file: files){
            musicFiles.put(file, "Resources/" + file + ".wav");
        }
    }

    public void playSound(String fileName){
        {
            try{
                File soundFile = new File(musicFiles.get(fileName));
                AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
                AudioFormat format = ais.getFormat();
                DataLine.Info info =new DataLine.Info(Clip.class, format);
                Clip clip =(Clip)AudioSystem.getLine(info);
                clip.open(ais);
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-10);
                clip.start();
                if(fileName == "Music") {
                    clip.loop(clip.LOOP_CONTINUOUSLY);
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


    @Override
    public void run() {
//        playSound(musicFiles.get(currentSongIndex));

    }
}
