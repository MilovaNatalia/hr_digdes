package com.digdes.services;

import com.digdes.dto.SettingsDto;
import com.digdes.notifier.Notifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotifierSettingsDataService {

    @Autowired
    private Notifier notifier;

    //todo: map to settings
    @Transactional
    public boolean update(SettingsDto info) {
        //todo: map to settings
        //todo: notifier.setUpSettings
        return false;
    }
}
