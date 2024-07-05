package hackerrank.java.javamd5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Solution {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String string = bufferedReader.readLine();

//        MD5 md5 = new MD5();
//        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(string.getBytes());
        byte[] digestBytes = messageDigest.digest();

//        String hash = DatatypeConverter.printHexBinary(digestBytes).toLowerCase();
//        System.out.println(hash);
    }
}