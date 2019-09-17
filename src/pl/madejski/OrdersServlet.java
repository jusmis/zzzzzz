package pl.madejski;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.madejski.dao.OrderDao;

@WebServlet(name = "OrderServlet", urlPatterns = {"/orders"})
public class OrdersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrdersServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderDao dao = new OrderDao();
		request.setAttribute("products", dao.findAll());
		request.getRequestDispatcher("/product.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchPhrase = request.getParameter("search");
		if(searchPhrase == null) {
			doGet(request, response);
		}
		else {
			OrderDao dao = new OrderDao();
			request.setAttribute("orders", dao.findByOrderId(Long.valueOf(searchPhrase)));
			request.getRequestDispatcher("/order.jsp").forward(request, response);
		}
	}

}

