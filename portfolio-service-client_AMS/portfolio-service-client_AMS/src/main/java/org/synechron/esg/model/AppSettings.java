package org.synechron.esg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;

@Document(collection = "appsettings")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppSettings implements Serializable{

	private static final long serialVersionUID = 1L;

    @Id
	@Field(name="name")
	private String name;
    
	@Field(name="value")
    private String value;
}