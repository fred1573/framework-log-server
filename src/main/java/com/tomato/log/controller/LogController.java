package com.tomato.log.controller;

import com.tomato.framework.log.support.Paging;
import com.tomato.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Hunhun
 */
@Controller
@RequestMapping("/log/query")
public class LogController {

    @Autowired
    private LogService logService;

    @RequestMapping
    @ResponseBody
    public Result query(@RequestParam String queryParams) {
        Paging paging = logService.queryLog(queryParams);
        return new Result(paging.getResult(), paging);
    }

    @RequestMapping("/group")
    @ResponseBody
    public Result group(@RequestParam String queryParams) {
        Object result = logService.groupLog(queryParams);
        return new Result(result, null);
    }

}
