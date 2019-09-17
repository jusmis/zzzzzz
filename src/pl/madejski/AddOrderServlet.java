package pl.madejski;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.madejski.dao.OrderDao;
import pl.madejski.model.Order;

@WebServlet("/products/add")
public class AddOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/addorder.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Long orderId = Long.valueOf(request.getParameter("orderID"));
			if(orderId == null || orderId== 0) throw new NullPointerException("Empty field");
			Long bookId = Long.valueOf(request.getParameter("bookID"));
			if(bookId == null || bookId== 0) throw new NullPointerException("Empty field");
			Long clientId = Long.valueOf(request.getParameter("clientID"));
			if(clientId == null || clientId== 0) throw new NullPointerException("Empty Field");
			Order newOrder = new Order(orderId, bookId,clientId);
			OrderDao orderDao = new OrderDao();
			
			orderDao.save(newOrder);
			
			response.sendRedirect("/IDDBlab2/orders");
		} catch (Exception e) {
			request.setAttribute("error", true);
			request.setAttribute("errorMessage", e.getMessage());
			request.getRequestDispatcher("/addorder.jsp").forward(request, response);
		}
	}

}
