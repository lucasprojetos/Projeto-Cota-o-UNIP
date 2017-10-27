package br.com.cotasmart.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.cotasmart.dao.UsuarioDao;
import br.com.cotasmart.modelo.Usuario;

@Transactional
@Controller
public class LoginController {

	@RequestMapping("login")
	public String login() {
		return "geral/login";
	}

	@RequestMapping("efetuaLogin")
	public String efetuaLogin(Usuario usuario, HttpSession session) {
		UsuarioDao dao = new UsuarioDao();
		if (dao.existeUsuario(usuario)) {
			session.setAttribute("usuarioLogado", usuario);
			return "inicio";
		}
		return "redirect:login";
	}

}