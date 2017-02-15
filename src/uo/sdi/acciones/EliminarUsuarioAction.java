package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.AdminService;
import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class EliminarUsuarioAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String login = request.getParameter("login");
		
		AdminService adminService = Services.getAdminService();
		UserService userService = Services.getUserService();
		
		User user;
		try {
			user = userService.findLoggableUser(login);
			adminService.deepDeleteUser(user.getId());
		} catch (BusinessException b) {
			request.setAttribute("error", b.getMessage());
			Log.debug("Algo ha ocurrido al eliminar al usuario");
			return "FRACASO";
		}

		return "EXITO";
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}

}
