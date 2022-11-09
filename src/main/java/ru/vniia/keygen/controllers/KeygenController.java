package ru.vniia.keygen.controllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.vniia.keygen.domain.Key;
import ru.vniia.keygen.domain.Organization;
import ru.vniia.keygen.domain.User;
import ru.vniia.keygen.repos.KeyRepo;
import ru.vniia.keygen.repos.OrganisationRepo;
import ru.vniia.keygen.services.FileTextContentService;
import ru.vniia.keygen.services.keyGen.KeyGenService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("/keygen")
public class KeygenController {

    @Autowired
    private KeyRepo keyRepo;
    @Autowired
    private OrganisationRepo organizationRepo;
    @Autowired
    private KeyGenService keyGenService;
    @Autowired
    private FileTextContentService fileContentService;

    @GetMapping()
    public String keygen(Model model) {
        model.addAttribute("key", new Key());
        model.addAttribute("organisations", organizationRepo.findAll());
        return "keygen";
    }

    @PostMapping(value = "/addOrganisation", params = "add")
    public String addOrganisation(String organisationToAddName) {
        Organization newOrganisation = new Organization(organisationToAddName);
        organizationRepo.save(newOrganisation);
        return "redirect:/keygen";
    }

    @PostMapping(value = "/addOrganisation", params = "remove")
    public String removeOrganisation(@RequestParam("organisationForEdit") Organization organisationToRemove, Model model) {
        boolean isOkToRemove = keyRepo.findAll().stream()
                .noneMatch(key -> key.getOrganization().getName().equals(organisationToRemove.getName()));
        if (isOkToRemove) {
            organizationRepo.delete(organisationToRemove);
            return "redirect:/keygen";
        } else {
            model.addAttribute("errorMessage", "Имеется ключ с этой организацией!");
            keygen(model);
            return "keygen";
        }
    }

    @PostMapping()
//    @ResponseBody
    public String generateKey(@AuthenticationPrincipal User user,
                              @RequestParam("serialFile") MultipartFile serialFile,
                              Key key,
                              Model model,
                              @RequestParam("expireDateCalendar")
                              @DateTimeFormat(pattern = "yyyy-MM-dd") Calendar expireDateCalendar,
                              HttpServletResponse response
    ) {
        model.addAttribute("organisations", organizationRepo.findAll());

        Date expireDate = expireDateCalendar.getTime();
        if (!isKeyExpireDateOk(expireDate)) {
            model.addAttribute("errorMessage", "Неверная дата окончания работы ключа");
//            endOfGenerate(response);
            return "keygen";
        }

        String serial = fileContentService.getContent(serialFile);
        if (serial == null) {
            model.addAttribute("errorMessage", "Ошибка чтения файла серийного номера!");
//            endOfGenerate(response);
            return "keygen";
        }

        String keyString = keyGenService.generate(serial, expireDateCalendar);
        if (keyString == null) {
            model.addAttribute("errorMessage", "Ошибка генерации ключа! Проверьте введенные данные");
//            endOfGenerate(response);
            return "keygen";
        }

        configurateKey(key, serial, keyString, user, expireDate);
        keyRepo.save(key);
        model.addAttribute("errorMessage", "ОКЕЙ");
        boolean result = returnLicenceKey(response, key);
        if (!result) model.addAttribute("errorMessage", "Ошибка загрузки ключа!");
        return "keygen";
    }

    private void endOfGenerate(HttpServletResponse response) {
        try {
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private boolean isKeyExpireDateOk(Date expireDate) {
        return expireDate.after(new Date(System.currentTimeMillis()));
    }

    private void configurateKey(Key key, String serial, String keyString, User author, Date expireDate) {
        key.setKey(keyString);
        key.setSerial(serial);
        key.setGenerateDate(Calendar.getInstance().getTime());
        key.setAuthor(author);
        key.setExpireDate(expireDate);
    }

    private boolean returnLicenceKey(HttpServletResponse response, Key key) {
        boolean result = true;
        String fileName = String.format("key%s.txt", key.getGenerateDate().getTime());
        File licenceFile = fileContentService.getFileWithContent(fileName, key.getKey());
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        try {
            response.setContentType("text/plain");
            FileInputStream fileInputStream = new FileInputStream(licenceFile);
            ServletOutputStream responseOutputStream = response.getOutputStream();
            IOUtils.copy(fileInputStream, responseOutputStream);
            responseOutputStream.close();
            fileInputStream.close();
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        }
        licenceFile.delete();
        return result;
    }

}
