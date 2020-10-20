package com.tsl.msmservice.service;

import java.util.Map;

public interface msmService {
    boolean send(Map<String, Object> param, String phone);
}
