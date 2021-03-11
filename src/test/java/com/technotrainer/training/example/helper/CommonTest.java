package com.technotrainer.training.example.helper;

import com.technotrainer.training.example.controller.WebPageController;
import com.technotrainer.training.example.controller.WebPageControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class CommonTest {

    Common common;
    private static final Logger logger = LoggerFactory.getLogger(Common.class);
    @Before
    public void setup(){
        common = new Common();

    }

    @Test
    public void test1() {
        common.test();
    }
}