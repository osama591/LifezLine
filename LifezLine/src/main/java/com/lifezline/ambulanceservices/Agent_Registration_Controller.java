package com.lifezline.ambulanceservices;

import static com.bafl.ambulance.constants.ConstantEnum.RecordAlreadyExist_CODE;
import static com.bafl.ambulance.constants.ConstantEnum.RecordAlreadyExist_DESC;
import static com.bafl.ambulance.constants.ConstantEnum.Resp_Success_CODE;
import static com.bafl.ambulance.constants.ConstantEnum.Resp_Success_DESC;
import static com.bafl.ambulance.constants.ConstantEnum.Success_CODE;
import static com.bafl.ambulance.constants.ConstantEnum.Success_DESC;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifezline.ambulance.GenericResponses.GenericResponse;
import com.lifezline.ambulancemodel.AgentDetail;
import com.lifezline.sessionfactories.HibernateSessionFactory;

@RestController
public class Agent_Registration_Controller {

	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(Agent_Registration_Controller.class.getName());

	@RequestMapping(method = RequestMethod.POST, value = "/AgentRegistration")

	@ResponseBody
	public GenericResponse<TreeMap<String, String>> getnadaraparameter(@RequestBody(required = false) String parameters)
			throws JSONException, URISyntaxException, HttpException, IOException, ParseException,
			XPathExpressionException, ParserConfigurationException, SAXException, SQLException {

		Transaction tx = null;

		Session hbmSession = HibernateSessionFactory.getSession();

		GenericResponse<TreeMap<String, String>> response1 = new GenericResponse<TreeMap<String, String>>();

		TreeMap<String, String> response = new TreeMap<String, String>();

		AgentDetail agentdetail = new ObjectMapper().readValue(parameters, AgentDetail.class);

		try {

			tx = hbmSession.beginTransaction();

			// some action
	
			Query query = hbmSession.createQuery("from AGENTDETAIL where agent_Phone_number = :Contact_Number");
			query.setParameter("Contact_Number", agentdetail.getAgent_Phone_number());

			if (query.list().isEmpty()) {

				hbmSession.save(agentdetail);

				response.put("code", Success_CODE);
				response.put("Description", Success_DESC);

				logger.info("\n\n===============================================\n\n");
				logger.info("\n\nAgent Registration method: Success\n\n");

				logger.info("\n\nNew Agent Added in Database\n\n");

				logger.info("\n\n===============================================\n\n");
			}

			else {

				response.put("code", RecordAlreadyExist_CODE);
				response.put("Description", RecordAlreadyExist_DESC);

				logger.info("\n\n=============================================\n\n");
				logger.info(" Record Already Exist with ");

				logger.info("\n\n=================================================\n\n");
			}

			if (response.get("code").equals(Success_CODE)) {
				response1.setCode(Resp_Success_CODE);
				response1.setDescription(Resp_Success_DESC);
				response.remove("code");
				response.remove("Description");
				response1.setData(response);

			} else {
				String Code = response.get("code");

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
