package ctrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Start
 */
@WebServlet({"/Start", "/Startup", "/Startup/*"})
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//below variables are used so the client can recalculate payments with differing interest rates
	private double savedPrincipal, savedPeriod;
	
	private boolean varsInitialized = false;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Start() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//System.out.println("doGet called!");
		//OSAP parameters
		response.sendRedirect(this.getServletContext().getContextPath() + "/UI.jspx");
		double principal, period, interest, fixedInterest, graceInterest, gracePeriod;
		
		principal = Double.parseDouble(getServletContext().getInitParameter("principal"));
		period = Double.parseDouble(getServletContext().getInitParameter("period"));
		interest = Double.parseDouble(getServletContext().getInitParameter("interest"));
		fixedInterest = Double.parseDouble(getServletContext().getInitParameter("fixedInterest"));
		gracePeriod = Double.parseDouble(getServletContext().getInitParameter("gracePeriod"));
		
		varsInitialized = true;
		
		principal = (request.getParameter("principal") == null) ? principal : Double.parseDouble(request.getParameter("principal"));
		period = (request.getParameter("period") == null) ? period : Double.parseDouble(request.getParameter("period"));
		interest = (request.getParameter("interest") == null) ? interest : Double.parseDouble(request.getParameter("interest"));

		savedPrincipal = principal;
		savedPeriod = period;
		
		PrintWriter p = response.getWriter();
		
		if (request.getRequestURI().contains("/Startup/YorkBank")) {
			response.sendRedirect(this.getServletContext().getContextPath() + "/Start");
		}
		
		//p.append("\nContext arg: ").append(this.getServletContext().getInitParameter("Param1"));
		
		p.append("\n---- Monthly Payments ----");
		p.append("\nBased on Principal=" + principal + " Period=" + period + 
				" Interest=" + interest);
		
		double monthlyInt = (interest / 12.0) * .01;
		//double monthlyPayments = principal * Math.pow(1+monthlyInt, period) * (monthlyInt)
		//		/ (Math.pow(1+monthlyInt, period) - 1);
		
		double monthlyPayments = monthlyInt * principal / (1 - Math.pow(1+monthlyInt, -period));
		
		DecimalFormat d = new DecimalFormat("##.0");
		p.append("\nMonthly payments: " + d.format(monthlyPayments));
		//The below code is to generate an exception to test the exception page
		//int[] ex = {1, 2, 3};
		//int genException = ex[5];
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
