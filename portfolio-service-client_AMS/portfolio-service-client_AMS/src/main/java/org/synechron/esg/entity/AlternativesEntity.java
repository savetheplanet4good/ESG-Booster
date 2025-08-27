package org.synechron.esg.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.synechron.esg.model.AlternativeCacheKey;
import org.synechron.esg.model.AlternativeResponse;
import org.synechron.esg.model.AlternativesObject;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Document(collection = "alternative")

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor

@Data
public class AlternativesEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@JsonProperty("_id")
	private Integer _id;

	@JsonProperty("alternativeResponse")
    private AlternativeResponse  alternativeResponse;

	@JsonProperty("alternativeCacheKey")
    private AlternativeCacheKey alternativeCacheKey;

	@JsonProperty("created_date")
	private CustomDate createdDate;

	@JsonProperty("_class")
	private String  _class;
	
  	public AlternativesObject toAlternativesObject(){
  		AlternativesObject object = new AlternativesObject();
  		object.setAlternativeCacheKey(alternativeCacheKey);
		object.setAlternativeId(_id);
		object.setAlternativeResponse(alternativeResponse);
		object.setCreatedDate(createdDate.createdDate.get(0));
	  	return object;
  	}

  	@FieldDefaults(level = AccessLevel.PRIVATE)
	@NoArgsConstructor
	@AllArgsConstructor
 	static  class CustomDate {
		@JsonProperty("$date")
		private List<Date> createdDate;
	}
}


