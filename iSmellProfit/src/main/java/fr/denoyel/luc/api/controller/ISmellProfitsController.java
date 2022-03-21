package fr.denoyel.luc.api.controller;

import fr.denoyel.luc.application.service.ISmellProfitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/profit")
@AllArgsConstructor
public class ISmellProfitsController {

    private ISmellProfitService service;


    @GetMapping()
    private void createExcelFile() {
        service.createExcelFileForProfit();
    }
}
