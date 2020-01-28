package com.nowhere.jasyptoverjpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DefaultController {

    @Autowired
    InfoRepository infoRepo;

    @RequestMapping("enc")
    public Info enc(@RequestParam String param) {
        return infoRepo.save(Info.builder().name(param).encryptedName(param).build());
    }

    @RequestMapping("enc2")
    public Info enc2(@RequestParam String param, @RequestParam String param2) {
        return infoRepo.save(
                Info.builder().name(param).encryptedName(param)
                        .text(param2).encryptedText(param2).build()
        );
    }

    @RequestMapping("list")
    public List<Info> list() {
        return infoRepo.findAll();
    }
}
