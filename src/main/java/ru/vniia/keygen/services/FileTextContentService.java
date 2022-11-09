package ru.vniia.keygen.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class FileTextContentService {
    public String getContent(MultipartFile file){
        String content;
        try {
            content = new String(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return content;
    }

    public File getFileWithContent(String filePath, String content){
        File file = new File(filePath);
        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
            stream.write(content.getBytes());
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
}
