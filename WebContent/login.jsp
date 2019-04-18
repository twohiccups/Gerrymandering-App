<!DOCTYPE HTML>
<HTML>
    <head>
         <meta charset="UTF-8">
        <!-- Font Awesome Icons -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>

        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

        <!-- Our stylesheet -->
        <link rel="stylesheet" type="text/css" href="Resources/Styles/style.css">

        <style>
            .login-form {
                width: 500px;
                margin: 30px;
                padding: 20px;
                border-radius: 5px;
                background-color: lightgray;
            }
        </style>

        <title>Login</title>

    </head>

    <body>
        <div class="login-form">
            <h2 class="title">Login</h2>

            <form method="post" action="login">

                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="fa fa-user"></i></span>
                        <input type="text" class="form-control" name="username" placeholder="Username" required="required">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group mb-3">
                        <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                        <input type="password" class="form-control" name="password" placeholder="Password" required="required">
                    </div>
                </div>
                                
                <div class="error-msg">
                <%
					String error = (String) request.getAttribute("loginError");
					if (error != null)
                                            out.println("<font color=red size=12px>"+ error +"</font>");
		%>
                </div>

                <label>Don't have an account? <a href="register.jsp">Register.</a></label>
                <div class="form-group">
                    <input type="submit" class="btn btn-primary btn-lg"  value="Login">
                </div>
            </form>
        </div>
    </body>
</HTML>