package com.pos.app.controller;

import com.pos.app.dto.DaySalesDto;
import com.pos.app.model.DaySalesData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;

@Api
@RestController
public class DaySalesController {

    @Autowired
    DaySalesDto daySalesDto;

    @ApiOperation(value = "GET")
    @RequestMapping(path = "/api/daily-sales/{startDate}/{endDate}", method = RequestMethod.GET)
    public List<DaySalesData> get(@PathVariable ZonedDateTime startDate, @PathVariable ZonedDateTime endDate){
        return daySalesDto.getByDate(startDate,endDate);
    }

    @ApiOperation(value = "GENERATE REPORT")
    @RequestMapping(path = "/api/daily-sales/{startDate}/{endDate}", method = RequestMethod.POST)
    public void generateDaySalesInRange(@PathVariable ZonedDateTime startDate, @PathVariable ZonedDateTime endDate){
        daySalesDto.generateDaySales(startDate, endDate);
    }
}
