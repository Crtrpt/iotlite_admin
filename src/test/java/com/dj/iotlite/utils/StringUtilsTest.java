package com.dj.iotlite.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void parseNumber() {



        var padding=StringUtils.parsePadding("xx00000");
        System.out.println( StringUtils.parsePadding("xx00000"));


        System.out.println(String.format("%1$" + padding + "s", 18).replace(' ', '0'));


        System.out.println(Long.valueOf(String.format("%1$" + padding + "s", 9).replace(' ', '9')));


        System.out.println(  String.valueOf(10000).length()==5);

        System.out.println( StringUtils.parseNumber("xx"));
        System.out.println( StringUtils.parseStr("xx"));

        System.out.println( StringUtils.parseNumber("xx01"));
        System.out.println( StringUtils.parseStr("xx01"));

    }
}