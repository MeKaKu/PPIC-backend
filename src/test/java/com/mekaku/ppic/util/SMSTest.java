package com.mekaku.ppic.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SMSTest {

    @Test
    public void sendSms(){
        SMS.sendSms("946337","13278882303","1036");
    }

}