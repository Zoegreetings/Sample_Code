package edu.gatech.saad.p3.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class JsonObjectMappper extends ObjectMapper {

	private static final long serialVersionUID = 1L;
	public static final SimpleDateFormat date_sdf = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	public static final SimpleDateFormat date_only_sdf = new SimpleDateFormat(
			"yyyy-MM-dd");

	public JsonObjectMappper() {
		super();
		SimpleModule module = new SimpleModule();

		module.addSerializer(Date.class, dateSerializer);
		module.addDeserializer(Date.class, dateDeserializer);
		this.registerModule(module);
	}

	private JsonSerializer<Date> dateSerializer = new JsonSerializer<Date>() {

		@Override
		public void serialize(Date arg0, JsonGenerator arg1,
				SerializerProvider arg2) throws IOException,
				JsonProcessingException {
			arg1.writeString(date_sdf.format(arg0));

		}

	};

	private JsonDeserializer<Date> dateDeserializer = new JsonDeserializer<Date>() {

		@Override
		public Date deserialize(JsonParser arg0, DeserializationContext arg1)
				throws IOException, JsonProcessingException {
			String dateStr = arg0.getText();
			Date date = null;

			if (dateStr.indexOf(':') > -1) {
				try {
					date = date_sdf.parse(dateStr);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				try {
					date = date_only_sdf.parse(dateStr);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return date;
		}

	};

}