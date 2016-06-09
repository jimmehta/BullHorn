<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Bullhorn</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:include page="bootstrap.jsp"></jsp:include>
  </head>
  <body>
    <h1>Login</h1>

    <div class="container">
      <form class="form-signin" method="post" action="LoginServlet">
        <h2 class="form-signin-heading">${message}Please sign in</h2>
        <label for="inputEmail" class="sr-only">Email address</label>
        <input name="email" type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
        <input type="hidden" name="action" id="action" value="login"/>
        <label for="inputPassword" class="sr-only">Password</label>
        <input name="password"  type="password" id="inputPassword" class="form-control" placeholder="Password" required>
       
       <!-- remove checkbox until you add feature 
        <div class="checkbox">
          <label>
            <input name="remember" type="checkbox" value="remember-me"> Remember me
          </label>
        </div>
        -->
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>
    <a href="adduser.jsp">Join</a>
    </div> <!-- /container -->


    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>