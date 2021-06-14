/**
 *
 * @author Jedaboy/Mateus Oliveira/Guilherme Leme
 */


import com.mysql.cj.log.Log;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author jedaf
 */
public class Conversor {

    private AudioFormat sourceFormat;
    private long length;
    private boolean erase;

    public byte[] mp3toWav(InputStream mp3Data) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // abrimos a Stream que veio de nosso banco de dados
        AudioInputStream mp3Stream = AudioSystem.getAudioInputStream(mp3Data);
        sourceFormat = mp3Stream.getFormat();
        length = mp3Stream.getFrameLength();
        // Cria o objeto audioFormat do tipo desejado
        // ainda não é (wav)
        AudioFormat convertFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                sourceFormat.getSampleRate(), 16,
                sourceFormat.getChannels(),
                sourceFormat.getChannels() * 2,
                sourceFormat.getSampleRate(),
                false);
        // cria uma stream com o formato desejado, porem faltam informações que so estão presentes em um arquivo file
        AudioInputStream converted = AudioSystem.getAudioInputStream(convertFormat, mp3Stream);
        // criamos o arquivo file e pegamos infos necessarias para a montagem do audio no futuro
        sourceFormat = converted.getFormat();
        length = converted.getFrameLength();
        File file = File.createTempFile("wav", ".");
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
         // criamos o arquivo
        AudioSystem.write(converted, AudioFileFormat.Type.WAVE, file);
         // passamos o arquivo para uma stream agora com as infos mark/reset
         InputStream iS = AudioSystem.getAudioInputStream(file);

         // passamos para um array de bits
          byte[] bytes = IOUtils.toByteArray(iS);
          iS.close();

         //informamos se a exclusão do arquivo temporario na pasta %temp% foi bem sucedido  
         erase = file.delete();
        // enviamos o arry de bytes
        return bytes;
    }

    public AudioFormat getAudioFormat() {
        return sourceFormat;
    }

    public long getFrameLenght() {
        return length;
    }
     public boolean getBool() {
        return erase;
    }

}
