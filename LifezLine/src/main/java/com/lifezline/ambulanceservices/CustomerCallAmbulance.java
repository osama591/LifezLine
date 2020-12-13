package com.lifezline.ambulanceservices;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.http.HttpException;
import org.apache.http.ParseException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.baf.ambulance.ambulanceDAO.AgentDistanceMapping;
import com.bafl.ambulance.constants.ConstantEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifezline.ambulance.GenericResponses.GenericResponse;
import com.lifezline.ambulancemodel.AgentDetail;
import com.lifezline.ambulancemodel.CustomerAgentDistance;
import com.lifezline.ambulancemodel.CustomerDetail;
import com.lifezline.sessionfactories.HibernateSessionFactory;
import com.bafl.ambulance.constants.*;

@RestController
public class CustomerCallAmbulance {

	/*
	 * private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
	 * .getLogger(CustomerCallAmbulance.class.getName());
	 */

	HashMap distanceMap = new HashMap();
	double last_distance = 0;
	String last_agent_contact_number = "";

	@RequestMapping(method = RequestMethod.POST, value = "/CustomerAmbulanceCAll")
	@ResponseBody
	public GenericResponse<TreeMap<String, String>> getnadaraparameter(@RequestBody(required = false) String parameters)
			throws JSONException, URISyntaxException, HttpException, IOException, ParseException,
			XPathExpressionException, ParserConfigurationException, SAXException {

//		***************************************************

		java.util.Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String strDate = dateFormat.format(date);
		String RideNumber = strDate.replaceAll(" ", "").replace("-", "").replace(":", "");
//		**************************************************

		TreeMap<String, String> response = new TreeMap<String, String>();

		Transaction tx = null;

		Session hbmSession = HibernateSessionFactory.getSession();
		tx = hbmSession.beginTransaction();
		CustomerDetail Customer_Detail = new ObjectMapper().readValue(parameters, CustomerDetail.class);

//		************************************NEW ADDED END ***********************************************

		GenericResponse<TreeMap<String, String>> response1 = new GenericResponse<TreeMap<String, String>>();

		try {
			Query query = hbmSession.createQuery("from AGENTDETAIL where AgentRideStatus is NULL");

			if (!query.list().isEmpty()) {

				Iterator<AgentDetail> it = query.list().iterator();

				while (it.hasNext()) {

					AgentDetail Agent_detail = it.next();

					CustomerAgentDistance agent_dist = new CustomerAgentDistance();
					distanceMap = agent_dist.getdistance(Customer_Detail.getCust_long(), Customer_Detail.getCust_lat(),
							Agent_detail.getAgent_longitude(), Agent_detail.getAgent_longitude(), last_distance,
							last_agent_contact_number,

							Agent_detail.getAgent_Phone_number(), Customer_Detail.getCustnumber());

					AgentDistanceMapping entity = (AgentDistanceMapping) distanceMap
							.get(Customer_Detail.getCustnumber());
					last_agent_contact_number = entity.getAgentContactNumber();
					last_distance = entity.getDistance();
					response.put("RideNumber", Customer_Detail.getCustnumber() + RideNumber);

					response.put("code", ConstantEnum.Resp_Success_CODE);

					response.put("Description", ConstantEnum.Resp_Success_DESC);

				}
			}

			Query UpdateCustomerDetail = hbmSession
					.createQuery("from CustomerDetail where agent_Phone_number =:ContactNumber").setMaxResults(1);

			UpdateCustomerDetail.setParameter("ContactNumber", Customer_Detail.getCustnumber());

			Iterator<CustomerDetail> it = UpdateCustomerDetail.list().iterator();

			while (it.hasNext()) {

				CustomerDetail custdetail_model = it.next();
				custdetail_model.setCust_dest_long(Customer_Detail.getCust_dest_long());
				custdetail_model.setCust_dest_lat(Customer_Detail.getCust_dest_lat());

				custdetail_model.setCust_source_place(Customer_Detail.getCust_source_place());

				custdetail_model.setCust_dest_place(Customer_Detail.getCust_dest_place());

				custdetail_model.setRidenum(Customer_Detail.getCustnumber() + RideNumber);

				custdetail_model.setCust_lat(Customer_Detail.getCust_lat());

				custdetail_model.setCust_long(Customer_Detail.getCust_long());

				hbmSession.persist(custdetail_model);

			}

			AgentDistanceMapping mapping = (AgentDistanceMapping) distanceMap.get(Customer_Detail.getCustnumber());

			Query Update_Agent = hbmSession.createQuery("from AGENTDETAIL where agent_Phone_number=:Agent_Number");

			Update_Agent.setParameter("Agent_Number", mapping.getAgentContactNumber());

			Iterator<AgentDetail> Iterate_agent_detail = Update_Agent.list().iterator();
			while (Iterate_agent_detail.hasNext()) {

				AgentDetail agent_detail_update = Iterate_agent_detail.next();

				agent_detail_update.setRidenumber(Customer_Detail.getCustnumber() + RideNumber);
				hbmSession.persist(agent_detail_update);
			}

			if (response.get("code").equals(ConstantEnum.Success_CODE)) {
				response1.setCode(ConstantEnum.Resp_Success_CODE);
				response1.setDescription(ConstantEnum.Success_DESC);
				response.remove("code");
				response.remove("Description");
				response1.setData(response);

			} else {
				String Code = (String) response.get("code").substring(1, response.get("code").length());
				String Desc = response.get("Description");
				response1.setDescription(Desc);
				response1.setCode(Code);
				response.remove("code");
				response.remove("Description");
				response1.setData(response);

			}
			tx.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();

		} finally {

			hbmSession.close();

		}
		return response1;
	}

}
