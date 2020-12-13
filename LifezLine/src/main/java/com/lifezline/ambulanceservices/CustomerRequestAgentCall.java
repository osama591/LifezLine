package com.lifezline.ambulanceservices;

import java.io.IOException;
import java.net.URISyntaxException;
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
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.bafl.ambulance.constants.AgentAcceptEnum;
import com.lifezline.ambulance.GenericResponses.GenericResponse;
import com.lifezline.ambulancemodel.AgentDetail;
import com.lifezline.sessionfactories.HibernateSessionFactory;

import com.bafl.ambulance.constants.*;

@RestController
public class CustomerRequestAgentCall {

	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(CustomerRequestAgentCall.class.getName());

	@RequestMapping(method = RequestMethod.POST, value = "/CustomerRequestAgentLocation")
	@ResponseBody
	public GenericResponse<TreeMap<String, String>> getnadaraparameter(@RequestBody(required = false) String parameters)
			throws JSONException, URISyntaxException, HttpException, IOException, ParseException,
			XPathExpressionException, ParserConfigurationException, SAXException {

		GenericResponse<TreeMap<String, String>> response1 = new GenericResponse<TreeMap<String, String>>();

		TreeMap<String, String> response = new TreeMap<String, String>();

		JSONObject json = new JSONObject(parameters);

		String RideNumber = json.getString("RideNumber");

		Transaction tx = null;

		Session hbmSession = HibernateSessionFactory.getSession();
		tx = hbmSession.beginTransaction();

		try {

			Query query = hbmSession.createQuery("from AGENTDETAIL where ridenumber=:Ride");
			query.setParameter("Ride", RideNumber);

			if (!query.list().isEmpty()) {

				Iterator<AgentDetail> it = query.list().iterator();

				while (it.hasNext()) {
					AgentDetail agent_record = it.next();

					String agent_ride_acceptor = agent_record.getAgent_accept() == null ? ""
							: agent_record.getAgent_accept().toString().trim();

					if (agent_ride_acceptor.equals(AgentAcceptEnum.yes)) {

						response.put("code", ConstantEnum.Success_CODE);
						response.put("Description", ConstantEnum.Success_DESC);
						response.put("agent_name", agent_record.getAgent_FirstName());
						response.put("agent_carnumber", agent_record.getAgent_Vehcile_Number());
						response.put("agent_latitude", agent_record.getAgent_latitude());
						response.put("agent_longitude", agent_record.getAgent_longitude());
						response.put("agent_contact_number", agent_record.getAgent_Phone_number());
						response.put("agent_ride_number", agent_record.getRidenumber());

					} else {

						response.put("code", ConstantEnum.Success_CODE);
						response.put("Description", ConstantEnum.Success_DESC);
						response.put("agent_ride_number", RideNumber);

					}

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

					// response.toString();
					// return response1;
				}
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
