package com.apap.tutorial5.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.FlightService;
import com.apap.tutorial5.service.PilotService;

@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		FlightModel flight = new FlightModel();
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		flight.setPilot(pilot);
		
		ArrayList<FlightModel> flightTemp = new ArrayList<>();
		flightTemp.add(flight);
		pilot.setPilotFlight(flightTemp);
		
		model.addAttribute("flight", flight);
		model.addAttribute("pilot", pilot);
		return "addFlight";	
	}
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", params = {"addFlightRow"}, method = RequestMethod.POST)
	private String addFlightRow(@ModelAttribute PilotModel pilot, BindingResult bindingResult, Model model) {
		if(pilot.getPilotFlight()==null) {
			pilot.setPilotFlight(new ArrayList<FlightModel>());
		}
		
		pilot.getPilotFlight().add(new FlightModel());
		model.addAttribute("pilot", pilot);
		return "addFlight";
	}
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", params= {"submit"}, method = RequestMethod.POST)
	private String addFlightSubmit(@ModelAttribute PilotModel pilot) {
		PilotModel pilotNum = pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber());
		
		for (FlightModel flight : pilot.getPilotFlight()) {
			flight.setPilot(pilotNum);
			flightService.addFlight(flight);
		}
		
		return "add";
	}
	
	@RequestMapping(value = "/flight/delete", method = RequestMethod.POST)
	private String deleteFlight (@ModelAttribute PilotModel pilot, Model model) {
		for(FlightModel flight : pilot.getPilotFlight()) {
			flightService.deleteFlight(flight);
		}
		return "delete";
	}
	
    @RequestMapping (value = "/flight/update/{flightNumber}", method = RequestMethod.GET)
    private String update (@PathVariable ("flightNumber") String flightNumber, Model model) {
        FlightModel flight = flightService.getFlightDetailByFlightNum(flightNumber);
        
        model.addAttribute("flight", flight);
        return "updateFlight";
    }

    @RequestMapping (value = "/flight/update", method = RequestMethod.POST)
    private String updateFlightSubmit (@ModelAttribute FlightModel flight) {
        flightService.updateFlight(flight, flight.getFlightNumber());
        return "update";
    }
    
    @RequestMapping("/flight/view")
    public String view(Model model){
        List<FlightModel> flight = flightService.getFlights();

        model.addAttribute("flights", flight);
        return"view-flight";
    }
	
}
