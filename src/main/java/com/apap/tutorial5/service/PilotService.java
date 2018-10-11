package com.apap.tutorial5.service;

import java.util.List;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;

public interface PilotService {
	PilotModel getPilotDetailByLicenseNumber(String licenseNumber);
	void addPilot(PilotModel pilot);
	void deletePilot(PilotModel pilot);
	void updatePilot (PilotModel pilot, String licenseNumber);
	List<PilotModel> getPilotFlight();
}
