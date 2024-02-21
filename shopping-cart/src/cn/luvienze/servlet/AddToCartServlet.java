package cn.luvienze.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import cn.luvienze.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		try (PrintWriter out = response.getWriter()){
			ArrayList<Cart> cartList = new ArrayList<>();
			
			int id = Integer.parseInt(request.getParameter("id"));
			
			Cart cm = new Cart();
			cm.setId(id);
			cm.setQuantity(1);
			
			// go to session
			HttpSession session = request.getSession();
			ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
			
			// if there's no product
			if(cart_list == null) {
				cartList.add(cm);
				session.setAttribute("cart-list", cartList);
				response.sendRedirect("index.jsp");
			}
			else
			{
				cartList = cart_list;
				boolean exist = false;
								
				for(Cart c:cartList) {
					if(c.getId() == id) {
						exist = true;
						out.println("<h3 style='color:crimson; text-align:center'> Item already exists in cart. <a href='cart.jsp'>Go to Cart Page</a></h3>");
					}
				}
				if(!exist) {
					cartList.add(cm);
					response.sendRedirect("index.jsp");
				}
			}
			
		}
	}
}
