package controllers;

import java.util.Properties;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import play.mvc.*;

import views.html.*;

public class ProfileController extends Controller {

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

   public Result getProfile(){
	   JsonNode reqJson = request().body().asJson();
	   
	   Properties props = new Properties();
	   props.put("bootstrap.servers", "localhost:9092");
	   props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
	   props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

	   KafkaProducer<String,String> producer=new KafkaProducer<>(props);
//	   Producer<String, String> producer = new KafkaProducer<>(props);
       producer.send(new ProducerRecord<String, String>("my-profile", "", reqJson.toString()));

	   producer.close();
	   
	   System.out.println("Request Json :: "+reqJson);
	   return ok("Got it request json");
   } 
    
    
}
