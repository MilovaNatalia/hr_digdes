package com.digdes.controllers;

import com.digdes.dto.SettingsDto;
import com.digdes.notifier.Notifier;
import com.digdes.services.NotifierSettingsDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "notifier")
public class NotifierApiController {

    @Autowired
    private NotifierSettingsDataService dataService;

    @PutMapping(path = "/update")
    public ResponseEntity<?> update(@RequestBody SettingsDto info) {
        return ResponseEntity.ok(dataService.update(info));
    }
}
