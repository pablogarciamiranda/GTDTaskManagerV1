package uo.sdi.acciones;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.util.FieldsCheck;
import uo.sdi.dto.User;
import uo.sdi.dto.util.Cloner;

public class EditarUsuarioAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";
		List<String> errors = new ArrayList<String>();

		String newEmail = request.getParameter("newEmail");
		String newPassword = request.getParameter("newPassword");
		String newPassword2 = request.getParameter("newPassword2");
		String newLogin = request.getParameter("newLogin");
		String login = request.getParameter("login");
		
		UserService userService = Services.getUserService();
		User user = null;
		try {
			user = userService.findLoggableUser(login);
		} catch (BusinessException b) {
			b.printStackTrace();
			return "FRACASO";
		}
		
		User userClone = Cloner.clone(user);

		// If new fields are empty
		if (FieldsCheck.invalidFieldCheck(newEmail, newPassword, newPassword2, newLogin)) {
			errors.add("Existen campos vacios, por favor, rellenalos todos.");
			Log.debug(
					"El usuario [%s] no ha rellando los campos al actualizar datos",
					user.getLogin());
		} else {
			// Logica relacionada con la Vista
			if (!user.getEmail().equals(newEmail)) {
				userClone.setEmail(newEmail);
				Log.debug("Modificado el email de [%s] con el valor [%s]",
						userClone.getLogin(), newEmail);
			}
			if (user.getEmail().equals(newEmail)) {
				errors.add("El email no se ha modificado porque coincide con el anterior.");
				Log.debug("El email no se ha modificado porque coincide con el anterior.");
			}
			if (!user.getPassword().equals(newPassword)
					&& newPassword.equals(newPassword2)) {
				userClone.setPassword(newPassword);
				Log.debug("Modificada la password de [%s]",
						userClone.getLogin());
			}
			if (!newPassword.equals(newPassword2)) {
				errors.add("Las nuevas passwords no coinciden");
				Log.debug("Las nuevas passwords no coinciden");

			}
			if (user.getPassword().equals(newPassword)) {
				errors.add("La nueva password debe de ser distinta a la anterior.");
				Log.debug("La password introducida coincide con el anterior");
			}
			if (user.getLogin().equals(newLogin)){
				errors.add("El login debe de ser distinto al anterior.");
				Log.debug("El login debe de ser distinto al anterior.");
			}
			if (!user.getLogin().equals(newLogin)){
				userClone.setLogin(newLogin);
				Log.debug("Modificado el login de [%s] con el valor [%s]",
						login, newLogin);
			}
			try {
				userService = Services.getUserService();
				userService.updateUserDetails(userClone);

				request.setAttribute("user", userClone);
			} catch (BusinessException b) {
				errors.add("Algo ha ocurrido actualizando los datos del usuario. "
						+ b.getMessage());
				Log.debug(
						"Algo ha ocurrido actualizando los datos del usuario [%s]",
						user.getLogin());
			}
		}

		if (errors.size() > 0) {
			resultado = "FRACASO";
			request.setAttribute("errors", errors);
		}
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}