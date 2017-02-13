package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.business.AdminService;
import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import uo.sdi.dto.types.UserStatus;
import uo.sdi.dto.util.Cloner;
import alb.util.log.Log;

public class BloquearUsuarioAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";

		String login = request.getParameter("login");
		
		AdminService adminService = Services.getAdminService();
		UserService userService = Services.getUserService();
		
		User user;
		try {
			user = userService.findLoggableUser(login);
			adminService.disableUser(user.getId());
		} catch (BusinessException b) {
			request.setAttribute("error", b.getMessage());
			Log.debug("Algo ha ocurrido intentanto activar el usuario seleccionado");
			resultado = "FRACASO";
		}
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
