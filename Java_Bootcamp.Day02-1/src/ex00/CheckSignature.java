import java.io.*;
import java.util.*;

public class CheckSignature {

    private Map<String, String> signatures;

    public CheckSignature(Map<String, String> signatures) {
        this.signatures = signatures;
    }

    public Map<String, String> getSignatures() {
        return signatures;
    }

    public void setSignatures(Map<String, String> signatures) {
        this.signatures = signatures;
    }

    public void checkSignature(String path, String resultPath) {
        try (FileInputStream fin = new FileInputStream(path)) {
            byte[] buffer = new byte[8];
            fin.read(buffer);
            String curSignature = byteToHex(buffer);
            if (signatures.containsKey(curSignature)) {
                System.out.println("PROCESSED");
                try (FileOutputStream out = new FileOutputStream(resultPath, true)) { // true for writing in end
                    out.write(signatures.get(curSignature).getBytes());
                    out.write("\n".getBytes());
                } catch (IOException e) {
                    System.err.println(resultPath + " not found");
                }
            } else {
                System.out.println("UNDEFINED");
            }
        } catch (IOException e) {
            System.err.println(path + " not found");
        }
    }

    private static String byteToHex(byte[] b) {
        StringBuilder signature = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            signature.append(String.format("%02X ", b[i])); // to Hex
        }
        return signature.toString().trim(); // remove last space
    }

}