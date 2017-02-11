package uo.sdi.acciones;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.util.FieldsCheck;
import uo.sdi.dto.User;
import uo.sdi.dto.types.UserStatus;
import uo.sdi.persistence.UserDao;

public class RegistrarseAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";
		List<String> errors = new ArrayList<String>();

		String login = request.getParameter("loginUsuario"), email = request
				.getParameter("emailUsuario"), password = request
				.getParameter("passwordUsuario"), confirmContraseña = request
				.getParameter("confirmPasswordUsuario");

		if (FieldsCheck.invalidFieldCheck(login, email, password,
				confirmContraseña)) {
			errors.add("Existen campos vacios, por favor, rellenalos todos.");
			Log.debug("El usuario no ha rellenado los 4 campos al registrarse");
		} else {
			if (!password.equals(confirmContraseña)) {
				errors.add("Las contraseñas deben de ser iguales.");
				Log.debug("El usuario ha introducido contraseñas diferentes");
			} else {
				try {
					User user = new User();
					user.setLogin(login);
					user.setEmail(email);
					user.setPassword(password);
					user.setStatus(UserStatus.ENABLED);

					UserService userService = Services.getUserService();
					userService.registerUser(user);
				} catch (BusinessException b) {
					errors.add(b.getMessage());
					Log.debug("Ha ocurrido algo durante el registro y no se ha completado ");
				}
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
