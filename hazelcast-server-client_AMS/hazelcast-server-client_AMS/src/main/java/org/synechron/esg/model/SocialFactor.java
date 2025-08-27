package org.synechron.esg.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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