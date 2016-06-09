import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Bhuser;
import model.Bhpost;
import customTools.DbBullhorn;
import customTools.DBUtil;

@WebServlet("/PostServ")
public class PostServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PostServ() {
        super();
    }

    
    protected void doPost(HttpServletRequest request, 
    							HttpServletResponse response) 
    									throws ServletException, IOException {
		Date postdate = new Date();
		String posttext = request.getParameter("posttext");
		String nextURL = "/error.jsp";
		//need a reference to the session
		//get user out of session. If they don't exist then send them back to the login page.
		//kill the session while you're at it.
		HttpSession session = request.getSession();
		if (session.getAttribute("user")==null){
			//http://stackoverflow.com/questions/13638446/checking-servlet-session-attribute-value-in-jsp-file
			nextURL = "/login.jsp";
			session.invalidate();
			response.sendRedirect(request.getContextPath() + nextURL);
		    return;//return prevents an error
		}
		
		//get user information from session so we can connect to the db
		Bhuser user = (Bhuser)session.getAttribute("user");
		
		
		//get  a populated bhuser object since we'll add that to the post
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String query = "select u from Bhuser u where u.useremail=:email";
		TypedQuery<Bhuser> q = em.createQuery(query,Bhuser.class);
		q.setParameter("email",user.getUseremail());
		
		Bhuser bhUser = null;
		try {
			bhUser = q.getSingleResult();
			System.out.println("The user id is: " + bhUser.getBhuserid());
			nextURL = "/newsfeed";
		} catch (NoResultException e){
			System.out.println(e);
		} catch (NonUniqueResultException e){
			System.out.println(e);
		} finally {
			em.close();
		}
		
		//insert the post
		Bhpost bhPost = new Bhpost();
		bhPost.setBhuser(bhUser);
		bhPost.setPostdate(postdate);
		bhPost.setPosttext(posttext);
		
		DbBullhorn.insert(bhPost);
		
		//go to the newsfeed or error
		getServletContext().getRequestDispatcher(nextURL).forward(request, response);
		
	}

}