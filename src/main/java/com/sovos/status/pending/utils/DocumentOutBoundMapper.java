package com.sovos.status.pending.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sovos.status.pending.dto.DocumentOutbound;

public class DocumentOutBoundMapper implements RowMapper<DocumentOutbound>{
	
	@Override
	public DocumentOutbound mapRow(ResultSet rs, int rowNum) throws SQLException {
		DocumentOutbound doc =  new DocumentOutbound(rs.getLong("dedocid"),
				rs.getInt("decoid"),
				rs.getInt("detipodte"),
				rs.getLong("defolio"),
				rs.getString("dedigdigest"),
				rs.getInt("derutemi"));
		return doc;
	}

}
