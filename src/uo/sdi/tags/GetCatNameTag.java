package uo.sdi.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;

public class GetCatNameTag extends SimpleTagSupport{
	
private String catID;
	
	public void setCatID(String id) { this.catID=id;}
	
	public void doTag() throws JspException, IOException {
		
		long id=Long.parseLong(catID);
		String mensaje = "";
		try {
			mensaje = Services.getTaskService().findCategoryById(id).getName();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		getJspContext().getOut().write(mensaje);
		
	}

}
