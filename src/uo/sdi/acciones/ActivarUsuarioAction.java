package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.business.AdminService;
import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import uo.sdi.dto.types.UserStatus;
import uo.sdi.dto.util.Cloner;
import alb.util.log.Log;

public class ActivarUsuarioAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
String resultado = "EXITO";
		
		HttpSession session = request.getSession();
		User user = ((User) session.getAttribute("user"));
		
		//Lo hago en memoria
		User userClone = Cloner.clone(user);
		user.setStatus(UserStatus.ENABLED);
		
		try {
			//Y aqui en la base de datos
			AdminService userService = Services.getAdminService();
			userService.enableUser(user.getId());

			//Aqui se cambia el usuario clonado con el cambio hecho en memoria
			//por el de la sesion actual
			session.setAttribute("user", userClone);
		} catch (BusinessException b) { 
			request.setAttribute("error", b.getMessage());
			Log.debug(
					"Algo ha ocurrido intentanto activar el usuario [%s]: %s",
					user.getLogin(),b.getMessage());
			resultado = "FRACASO";
		}
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}

}
