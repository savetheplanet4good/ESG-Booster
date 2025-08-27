package org.synechron.esg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The type Governance factor.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GovernanceFactor implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

    private Double score;

}