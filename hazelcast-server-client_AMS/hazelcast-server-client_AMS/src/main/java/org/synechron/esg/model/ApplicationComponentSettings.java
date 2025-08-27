package org.synechron.esg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Document(collection = "app-component-settings")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApplicationComponentSettings implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Field(name="propertyDisplayName")
    private String propertyDisplayName;

    @Field(name="property")
    private String property;

    @Field(name="value")
    private Boolean value = Boolean.TRUE;

    @Field(name="disabledMessage")
    private String disabledMessage;

    @Override
    public String toString() {
        return "ApplicationComponentSettings{" +
                "propertyDisplayName='" + propertyDisplayName + '\'' +
                ", property='" + property + '\'' +
                ", value=" + value +
                ", disabledMessage='" + disabledMessage + '\'' +
                '}';
    }
}