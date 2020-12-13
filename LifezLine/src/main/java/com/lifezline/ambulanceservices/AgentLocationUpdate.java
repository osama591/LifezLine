/**
 * 
 */
package com.lifezline.ambulanceservices;

import com.bafl.ambulance.constants.ConstantEnum;
import static com.bafl.ambulance.constants.ConstantEnum.RecordAlreadyExist_CODE;
import static com.bafl.ambulance.constants.ConstantEnum.RecordAlreadyExist_DESC;
import static com.bafl.ambulance.constants.ConstantEnum.Resp_Success_CODE;
import static com.bafl.ambulance.constants.ConstantEnum.Resp_Success_DESC;
import static com.bafl.ambulance.constants.ConstantEnum.Success_CODE;
import static com.bafl.ambulance.constants.ConstantEnum.Success_DESC;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.http.HttpException;
import org.apache.http.ParseException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.bafl.ambulance.constants.AgentAcceptEnum;
import com.bafl.ambulance.constants.RIDESTATUS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifezline.ambulance.GenericResponses.GenericResponse;
import com.lifezline.ambulancemodel.AgentDetail;
import com.lifezline.ambulancemodel.CustomerDetail;
import com.lifezline.sessionfactories.HibernateSessionFactory;

/**
 * @author bal-admin
 *
 */

@RestController
public class AgentLocationUpdate {

	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(AgentLocationUpdate.class.getName());

	@RequestMapping(method = RequestMethod.POST, value = "/agentlocationupdate")
	@ResponseBody
	public GenericResponse<TreeMap<String, String>> getnadaraparameter(@RequestBody(required = false) String parameters)
			throws JSONException, URISyntaxException, HttpException, IOException, ParseException,
			XPathExpressionException, ParserConfigurationException, SAXException {
		GenericResponse<TreeMap<String, String>> response1 = new GenericResponse<TreeMap<String, String>>();
		TreeMap<String, String> response = new TreeMap<String, String>();

		Transaction tx = null;

		Session hbmSession = HibernateSessionFactory.getSession();

		AgentDetail agent_detail = new ObjectMapper().readValue(parameters, AgentDetail.class);

		try {

			tx = hbmSession.beginTransaction();

			// some action

			Query query = hbmSession.createQuery("from AGENTDETAIL where agent_Phone_number = :Contact_Number");
			query.setParameter("Contact_Number", agent_detail.getAgent_Phone_number());

			List<AgentDetail> agent_record = query.list();

			AgentDetail agent_record_to_persist = agent_record.get(0);

			if (!query.list().isEmpty()) {

				logger.info("\n\n agent_ride_number : ------>  " + agent_detail.getRidenumber());
				logger.info("\n\n agent_on_ride : ------>  " + agent_detail.getAgentRideStatus());
				logger.info("\n\n agent_accept : ------>  " + agent_detail.getAgent_accept());

				if (!((agent_detail.getRidenumber() == null)) && agent_detail.getAgentRideStatus().equals(RIDESTATUS.on)
						&& !(agent_detail.getAgent_accept().equals(AgentAcceptEnum.yes))) {

					response.put("RideNumber", agent_detail.getRidenumber());

					Query CustomerQueryDetail = hbmSession
							.createQuery("from CustomerDetail where ridenum = :RIDE_Number").setMaxResults(1);
					CustomerQueryDetail.setParameter("RIDE_Number", agent_detail.getRidenumber());

					List<CustomerDetail> cust_detail = CustomerQueryDetail.list();

					for (CustomerDetail Cust_info : cust_detail) {

						response.put("customername", Cust_info.getCustFirstname());

						response.put("customer_longitude", Cust_info.getCust_long());

						response.put("customer_latitude", Cust_info.getCust_lat());

//					***************************NEW ADDED **********************************

						response.put("customer_number", Cust_info.getCustnumber());
						response.put("customer_dest_lat", Cust_info.getCust_dest_lat());
						response.put("customer_dest_longitude", Cust_info.getCust_dest_long());

						response.put("customer_source_place", Cust_info.getCust_source_place());

						response.put("customer_dest_place", Cust_info.getCust_dest_place());

//					**************************** NEW ADDED ********************************

						response.put("agent_on_ride", ConstantEnum.Resp_Agent_On_Ride);
						response.put("code", ConstantEnum.Success_CODE);
						response.put("Description", ConstantEnum.Success_DESC);

					}
				}

				else if (agent_detail.getAgentRideStatus().equals("true")) {

					response.put("agent_on_ride", "true");
					response.put("code", ConstantEnum.Success_CODE);
					response.put("Description", ConstantEnum.Success_DESC);

				} else {

					response.put("agent_on_ride", "false");
					response.put("code", ConstantEnum.Success_CODE);
					response.put("Description", ConstantEnum.Success_DESC);

				
				
				}
				
		

				agent_record_to_persist.setAgent_latitude(agent_detail.getAgent_latitude());
				agent_record_to_persist.setAgent_longitude(agent_detail.getAgent_latitude());
				hbmSession.update(agent_record_to_persist);
				
				
				//

//				**********************************************************

				if (response.get("code").equals(ConstantEnum.Success_CODE)) {
					response1.setCode(ConstantEnum.Resp_Success_CODE);
					response1.setDescription(ConstantEnum.Success_DESC);
					response.remove("code");
					response.remove("Description");
					response1.setData(response);

				} else {

					response1.setDescription(ConstantEnum.Agent_Location_Update_Failure_DESC);
					response1.setCode(ConstantEnum.Agent_Location_Update_Failure_CODE);
					response.remove("code");
					response.remove("Description");
					response1.setData(response);
				}

			

				tx.commit();
			}

			else {

				response1.setDescription(ConstantEnum.Agent_Location_Update_Failure_DESC);
				response1.setCode(ConstantEnum.Agent_Location_Update_Failure_CODE);
				response.remove("code");
				response.remove("Description");
				response1.setData(response);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();

		} finally {

			hbmSession.close();

		}

		return response1;
	}

}
