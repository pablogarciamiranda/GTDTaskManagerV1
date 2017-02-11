package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.business.AdminService;
import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;

public class EliminarUsuarioAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		Long idUser = Long.valueOf(request.getParameter("id"));
		AdminService adminService = Services.getAdminService();
		
		try {
			adminService.deepDeleteUser(idUser);
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
