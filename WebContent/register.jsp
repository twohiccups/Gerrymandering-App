<!DOCTYPE HTML>
<HTML>
    <head>
        <!-- Font Awesome Icons -->
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

        <!-- Our stylesheet -->
        <link rel="stylesheet" type="text/css" href="Resourses/Styles/style.css">

        <style>
            .register-form {
                width: 500px;
                margin: 30px;
                padding: 20px;
                border-radius: 5px;
                background-color: lightgray;
            }

        </style>

        <title>Register</title>

    </head>

    <body>
        <div class="register-form">
            <h2 class="title">Register</h2>

            <form action="register" method="post">

                <div class="form-group">
                    <input type="text" class="form-control" name="firstname" placeholder="First Name" required="required">
                </div>

                <div class="form-group">
                    <input type="text" class="form-control" name="lastname" placeholder="Last Name" required="required">
                </div>

                <div class="form-group">
                    <input type="text" class="form-control" name="email" placeholder="email" required="required">
                </div>

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
                <div class="form-group">
                    <input type="submit" class="btn btn-primary btn-lg" value="Register">
                </div>

                <div class="error-msg">
                </div>


            </form>
        </div>

    </body>
</HTML>
