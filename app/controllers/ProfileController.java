package controllers;

import java.util.Properties;

import javax.inject.Inject;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.fasterxml.jackson.databind.JsonNode;
import com.profile.MyProfile.Profile;

import play.mvc.Controller;
import play.mvc.Result;

import views.html.index;

public class ProfileController extends Controller {
	
    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

   public Result getProfile(){
	   JsonNode reqJson = request().body().asJson();
	   
	   Properties producerProperties = setProperties();
	   
	   Profile.Builder profile=Profile.newBuilder();
	   
	   profile.setFirstName(reqJson.get("firstname").asText().toString());
	   profile.setLastName(reqJson.get("lastname").asText().toString());
	   
	   KafkaProducer<String,String> producer=new KafkaProducer<>(producerProperties);
//	   Producer<String, String> producer = new KafkaProducer<>(props);
       producer.send(new ProducerRecord<String, String>("my-profile", "MyProfile",profile.toString()));

	   producer.close();
	   
	   System.out.println("Request Json :: "+reqJson);
	   return ok("Consumer consuming result");
   } 
    
   private Properties setProperties(){
	   Properties props = new Properties();
	   props.put("bootstrap.servers", "localhost:9092");
	   props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	   props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	   return props;
   }
    
}
