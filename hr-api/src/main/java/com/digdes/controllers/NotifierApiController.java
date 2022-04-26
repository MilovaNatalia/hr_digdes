package com.digdes.controllers;

import com.digdes.dto.SettingsDto;
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

    @PutMapping(path = "/settings/update")
    public ResponseEntity<?> update(@RequestBody SettingsDto info) {
        if (info.getHost() == null || info.getPort() == null
            || info.getUsername() == null || info.getPassword() == null)
            return ResponseEntity.badRequest().body("All fields must not be null");
        return ResponseEntity.ok(dataService.update(info));
    }

}
