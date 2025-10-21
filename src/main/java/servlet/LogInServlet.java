package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.UserDAO;
import model.User;

/**
 * ログイン
 */
@WebServlet("/LogInServlet")
public class LogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		// 入力値の取得
    String mailaddress = request.getParameter("mailaddress");
    String password = request.getParameter("password");
    
    UserDAO userDAO = new UserDAO();
    User user = userDAO.findUserByEmail(mailaddress);

    
   

		
	}

}
