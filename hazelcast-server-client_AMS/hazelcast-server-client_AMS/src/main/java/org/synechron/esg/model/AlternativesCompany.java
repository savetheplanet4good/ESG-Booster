package org.synechron.esg.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativesCompany implements Serializable{
 

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String isin;

    private String companyName;

    private Double esgScore;
}
