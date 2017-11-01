package IHM;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

public class HashTest {
    private static String myHash = "MD5";
    private static File file;

    public static void main(String[] args){
        long debut = System.currentTimeMillis();
        file = new File("D:\\Machine Debian pour Marionnet\\Marionnet-IUT_Orsay_2016.ova");
        System.out.println(hash());
        System.out.println("Temps de construction : " + (System.currentTimeMillis() - debut));
    }

    private static String hash(){
        FileInputStream inputStream = null;
        try {
            MessageDigest md = MessageDigest.getInstance(myHash);
            inputStream = new FileInputStream(file);
            FileChannel channel = inputStream.getChannel();
            ByteBuffer buff = ByteBuffer.allocate(2048);
            while (channel.read(buff) != -1){
                buff.flip();
                md.update(buff);
                buff.clear();
            }
            byte[] hashValue = md.digest();
            return new String(hashValue);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
        finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
