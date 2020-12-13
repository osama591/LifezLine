package com.lifezline.ambulancemodel;

import java.util.HashMap;

import com.baf.ambulance.ambulanceDAO.AgentDistanceMapping;

public class CustomerAgentDistance {

	public HashMap<String, AgentDistanceMapping> getdistance(String cust_long, String cust_lat, String agent_long, String agent_lat,
			double last_distance, String last_agent_contact_number, String agent_contact_number, String Custnumber) {

		double current_distance = distance(Double.valueOf(cust_lat), Double.valueOf(cust_long),
				Double.valueOf(agent_lat), Double.valueOf(agent_long), "K");

		HashMap<String, AgentDistanceMapping> agentdetailobject = new HashMap<String, AgentDistanceMapping>();
		AgentDistanceMapping agentdistance = new AgentDistanceMapping();
		if (last_distance != 0) {

			if (last_distance < current_distance) {
			

				agentdistance.setAgentContactNumber(last_agent_contact_number);
				agentdistance.setDistance(last_distance);
				agentdetailobject.put(Custnumber, agentdistance);
			} else {
			

				agentdistance.setAgentContactNumber(agent_contact_number);
				agentdistance.setDistance(current_distance);
				agentdetailobject.put(Custnumber, agentdistance);
			}
		}
		else {
			
			agentdistance.setAgentContactNumber(agent_contact_number);
			agentdistance.setDistance(current_distance);
			agentdetailobject.put(Custnumber, agentdistance);
			
		}
		return agentdetailobject;
	}

	private static double distance(double cust_lat, double cust_long, double agent_lat, double agent_long,
			String unit) {
		if ((cust_lat == agent_lat) && (cust_long == agent_long)) {
			return 0;
		} else {
			double theta = cust_long - agent_long;
			double dist = Math.sin(Math.toRadians(cust_lat)) * Math.sin(Math.toRadians(agent_lat))
					+ Math.cos(Math.toRadians(cust_lat)) * Math.cos(Math.toRadians(agent_lat))
							* Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			if (unit.equals("K")) {
				dist = dist * 1.609344;
			} else if (unit.equals("N")) {
				dist = dist * 0.8684;
			}
			return (dist);
		}
	}

}