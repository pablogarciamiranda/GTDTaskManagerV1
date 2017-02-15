package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class ModificarUsuarioAction implements Accion{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String login = request.getParameter("login");
		UserService userService = Services.getUserService();
		User user;
		try {
			
			user = userService.findLoggableUser(login);
			request.setAttribute("user", user);
			
		} catch (BusinessException b) {
			request.setAttribute("error", b.getMessage());
			Log.debug("Algo ha ocurrido editando el usuario: %s", b.getMessage());
		
			return "FRACASO";
		}
		return "EXITO";
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}

}
