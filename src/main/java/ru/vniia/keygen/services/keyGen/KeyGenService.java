package ru.vniia.keygen.services.keyGen;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Calendar;

@Service
public class KeyGenService{
    public String generate(String serial, Calendar expireDate) {
        int year = expireDate.get(Calendar.YEAR);
        int month = expireDate.get(Calendar.MONTH) + 1;
        int day = expireDate.get(Calendar.DAY_OF_MONTH);

        String license = licenseGeneratorMock(year+month+day+serial);
        return license;
    }

    private String licenseGeneratorMock(String materialForGeneration){
        String license = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            byte[] licenceByteArr = messageDigest.digest((materialForGeneration).getBytes(StandardCharsets.UTF_8));
            license = Base64.getEncoder().encodeToString(licenceByteArr);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return license;
    }
}
