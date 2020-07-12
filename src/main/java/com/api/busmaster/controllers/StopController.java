package com.api.busmaster.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.busmaster.models.Stop;
import com.api.busmaster.services.StopService;

@RestController
public class StopController {
	@Autowired
	private StopService stopService;
	
	@GetMapping("/api/nextstop")
	public Stop getNextStop(@RequestParam("prevStopSeqNum")int prevStopSeqNum, @RequestParam("routeId")int routeId) {
		System.out.println("Request params: " + prevStopSeqNum + " " + routeId);
		return stopService.getNextStop(prevStopSeqNum, routeId);
	}
}
