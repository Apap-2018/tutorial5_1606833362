package com.apap.tutorial5.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.repository.FlightDB;

@Service
@Transactional
public class FlightServiceImpl implements FlightService{
	@Autowired
	private FlightDB flightDB;

	@Override
	public void addFlight(FlightModel flight) {
		flightDB.save(flight);
		
	}

	@Override
	public void deleteFlight(FlightModel flight) {
		flightDB.delete(flight);
	}
	
	@Override
	public FlightModel findFlight(Long id) {
		return flightDB.getOne(id);
	}

	@Override
	public void updateFlight(FlightModel flight, String flightNumber) {
		flight.setFlightNumber(flightNumber);
		flightDB.save(flight);
	}

    @Override
    public FlightModel getFlightDetailByFlightNum(String flightNumber) {
        return flightDB.findByFlightNumber(flightNumber);
    }

	@Override
	public List<FlightModel> getFlights() {
		return flightDB.findAll();
	}
	
	@Override
	public void deleteFlightById(long id) {
		flightDB.deleteById(id);;
	}

}
