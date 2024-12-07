package com.snhu.sslserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}

@RestController
class ServerController {

    @RequestMapping("/hash")
    public String myHash() {
        String data = "Hello Alex Surprenant!";
        String checksum = generateChecksum(data);

        return "<p>Data: " + data + "</p>" +
               "<p>Checksum (SHA-256): " + checksum + "</p>";
    }

    private String generateChecksum(String data) {
        try {
            // Create a MessageDigest instance with SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Generate the hash as a byte array
            byte[] hash = digest.digest(data.getBytes());
            // Convert the byte array to a hexadecimal string
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    // Helper function to convert byte array to hexadecimal string
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
