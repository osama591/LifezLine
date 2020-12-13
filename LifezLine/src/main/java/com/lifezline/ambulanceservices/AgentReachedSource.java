package com.lifezline.ambulanceservices;

import java.io.IOException;
import java.net.URISyntaxException;

import java.sql.SQLException;
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
import org.xml.sax.SAXException;

import com.bafl.ambulance.constants.ConstantEnum;
import com.lifezline.ambulance.GenericResponses.GenericResponse;
import com.lifezline.ambulancemodel.AgentDetail;
import com.lifezline.sessionfactories.HibernateSessionFactory;

public class AgentReachedSource {

	

	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(AgentReachedSource.class.getName());

	@RequestMapping(method = RequestMethod.POST, value = "/AgentReachedSource")
	@ResponseBody
	public GenericResponse<TreeMap<String, String>> getnadaraparameter(@RequestBody(required = false) String parameters)
			throws JSONException, URISyntaxException, HttpException, IOException, ParseException,
			XPathExpressionException, ParserConfigurationException, SAXException, SQLException {

		GenericResponse<TreeMap<String, String>> response1 = new GenericResponse<TreeMap<String, String>>();
		Transaction tx = null;

		Session hbmSession = HibernateSessionFactory.getSession();
		try {

			tx = hbmSession.beginTransaction();

			TreeMap<String, String> response = new TreeMap<String, String>();

			JSONObject json = new JSONObject(parameters);
			String RideNumber = json.getString("RideNumber");

			Query query = hbmSession.createQuery("from AGENTDETAIL where ridenumber=:RIDE");

			query.setParameter("RIDE", RideNumber);
			
			if (!query.list().isEmpty()) {
				Iterator<AgentDetail> it = query.list().iterator();
				while (it.hasNext()) {

					AgentDetail detail = it.next();
					detail.setAgent_reached_source("yes");

					detail.setAgentRideStatus("reached");

					response.put("agentname", detail.getAgent_FirstName());

					response.put("agentlatitude", detail.getAgent_latitude());

					response.put("agentlongitude", detail.getAgent_longitude());

					response.put("agentcontactnumber", detail.getAgent_Phone_number());

					response.put("code", ConstantEnum.Success_CODE);
					response.put("Description", ConstantEnum.Success_DESC);

					hbmSession.persist(detail);
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
				String Desc = (String) response.get("Description");
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
