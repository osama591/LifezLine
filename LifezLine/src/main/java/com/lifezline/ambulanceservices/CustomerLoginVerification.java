package com.lifezline.ambulanceservices;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.http.HttpException;
import org.apache.http.ParseException;
import org.hibernate.HibernateException;
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

import com.bafl.ambulance.constants.ConstantEnum;
import com.lifezline.ambulance.GenericResponses.GenericResponse;
import com.lifezline.ambulancemodel.CustomerDetail;
import com.lifezline.sessionfactories.HibernateSessionFactory;

import android.content.ClipData.Item;

@RestController
public class CustomerLoginVerification {

	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(CustomerLoginVerification.class.getName());

	@RequestMapping(method = RequestMethod.POST, value = "/Customerlogin")
	@ResponseBody
	public GenericResponse<TreeMap<String, String>> getnadaraparameter(@RequestBody(required = false) String parameters)
			throws JSONException, URISyntaxException, HttpException, IOException, ParseException,
			XPathExpressionException, ParserConfigurationException, SAXException {

		ResultSet rs = null;
		PreparedStatement preparedStatement = null;

		TreeMap<String, String> response = new TreeMap<String, String>();
		GenericResponse<TreeMap<String, String>> response1 = new GenericResponse<TreeMap<String, String>>();

		JSONObject json = new JSONObject(parameters);
		String username = json.getString("username");
		String password = json.getString("password");

		Transaction tx = null;

		Session hbmSession = HibernateSessionFactory.getSession();

		tx = hbmSession.beginTransaction();

		Query query = hbmSession.createQuery("from CustomerDetail where Custnumber=:number and password=:Passcode");

		query.setParameter("number", username);

		query.setParameter("Passcode", password);

		try {

			if (!query.list().isEmpty()) {

				Iterator<CustomerDetail> it = query.list().iterator();

				while (it.hasNext()) {

					CustomerDetail detail = it.next();

					response.put("CustFirstname", detail.getCustFirstname());
					response.put("CustLastname", detail.getCustLastname());
					response.put("Custnumber", detail.getCustnumber());

					response.put("code", ConstantEnum.Success_CODE);
					response.put("Description", ConstantEnum.Success_DESC);

				}

			}

			else {

				response.put("code", ConstantEnum.AgentDoesnot_Exist_CODE);
				response.put("Description", ConstantEnum.AgentDoesnot_Exist_DESC);
				logger.info(" Record Already Exist with ");

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
