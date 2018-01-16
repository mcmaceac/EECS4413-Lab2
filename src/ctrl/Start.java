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
		
		response.sendRedirect(this.getServletContext().getContextPath() + "/UI.jspx");
		double principal, period, interest, fixedInterest, graceInterest, gracePeriod;
		
		//boolean gracePeriodEnabled = request.getParameter("gracePeriod").equals("on");
		
		principal = Double.parseDouble(getServletContext().getInitParameter("principal"));
		period = Double.parseDouble(getServletContext().getInitParameter("period"));
		interest = Double.parseDouble(getServletContext().getInitParameter("interest"));
		fixedInterest = Double.parseDouble(getServletContext().getInitParameter("fixedInterest"));
		gracePeriod = Double.parseDouble(getServletContext().getInitParameter("gracePeriod"));
		
		//submit has been pressed, get the parameters that were entered
		if (request.getParameter("submit") != null) {
			principal = (request.getParameter("principal") == null) ? principal : Double.parseDouble(request.getParameter("principal"));
			period = (request.getParameter("period") == null) ? period : Double.parseDouble(request.getParameter("period"));
			interest = (request.getParameter("interest") == null) ? interest : Double.parseDouble(request.getParameter("interest"));
		}
		
		
		PrintWriter p = response.getWriter();
		
		double monthlyInt = ((fixedInterest + interest) / 12.0) * .01;
		double monthlyPayments = monthlyInt * principal / (1 - Math.pow(1+monthlyInt, -period));
		
		/*if (gracePeriodEnabled) {
			graceInterest = principal * ((interest + fixedInterest) / 12.0) * gracePeriod;
			monthlyPayments += (graceInterest / gracePeriod);
		}*/
		
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
