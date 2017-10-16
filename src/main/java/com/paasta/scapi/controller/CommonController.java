package com.paasta.scapi.controller;

import com.paasta.scapi.common.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class CommonController extends Common {

    protected Logger logger = LoggerFactory.getLogger(getClass());
}
