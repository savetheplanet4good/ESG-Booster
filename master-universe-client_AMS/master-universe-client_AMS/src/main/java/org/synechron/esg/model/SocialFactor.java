package org.synechron.esg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SocialFactor implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

    private Double score;

}