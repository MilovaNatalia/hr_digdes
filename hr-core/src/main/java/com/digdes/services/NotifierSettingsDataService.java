package com.digdes.services;

import com.digdes.dto.SettingsDto;
import com.digdes.notifier.Notifier;
import com.digdes.notifier.SmtpSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotifierSettingsDataService {

    @Autowired
    private Notifier notifier;

    @Transactional
    public boolean update(SettingsDto info) {
        SmtpSettings settings = mapDtoToSettings(info);
        return notifier.setUpSettings(settings);
    }

    private SmtpSettings mapDtoToSettings (SettingsDto dto){
        return new SmtpSettings(dto.getHost(),
                dto.getPort(), dto.getUsername(), dto.getPassword());
    }
}
