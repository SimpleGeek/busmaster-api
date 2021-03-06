package com.api.busmaster.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.busmaster.daos.StopDao;
import com.api.busmaster.models.Stop;

@Service
public class StopService {
	@Autowired
	private StopDao stopDao;
	
	@Autowired
	private RiderService riderService;
	
	public Stop getNextStop(int prevStopSeqNum, int routeId) {
		Stop nextStop = stopDao.getNextStop(prevStopSeqNum, routeId);
		
		// Retrieve list of riders for this stop
		nextStop.setRiders(riderService.getRidersForStop(nextStop.getStopId()));
		
		return nextStop;
	}
	
	public void insertStopIntoRoute(Stop stop) {
		// Shift all stops up one sequence number,
		// starting with the existing stop currently 
		// in the new stop's position.
		stopDao.shiftStopSequencesUp(stop.getSeqNum(), stop.getRouteId());
		
		// Next, insert the new stop in the newly created hole.
		stopDao.insertStop(stop);
		
		// Now, add stop id of just-inserted stop to each rider
		int stopId = stopDao.getStopIdBySeqNumAndRouteId(stop.getSeqNum(), stop.getRouteId());
		stop.getRiders().forEach(r -> r.setStopId(stopId));
		
		// Finally, insert the riders for the stop
		riderService.insertRiders(stop.getRiders());
	}
}
