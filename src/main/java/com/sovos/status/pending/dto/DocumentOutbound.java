package com.sovos.status.pending.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DocumentOutbound {

	private Long dedocid;
	private Integer decoid;
	private Integer detipodte;
	private Long defolio;
	private String dedigdigest;
	private Integer derutemi;
}
