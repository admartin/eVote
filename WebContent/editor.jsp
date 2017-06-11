<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>eVote</title>
    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="css/sb-admin.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet"
          type="text/css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script>
        function myFunction() {
            var candidate = prompt("Please enter the candidate's name", "Candidate");
            var party = prompt("Please enter the candidate's party", "Party");
            var inc = prompt("Is this candidate an incumbent?", "No");
            if (candidate != null) {
                document.getElementById("demo").innerHTML =
                document.getElementById("demo").innerHTML +
                "Added " + candidate + " <br>";
            }
    </script>
</head>
<body>
    <div id="wrapper">
        <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="eo-home.html">eVote</a>
            </div>
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> Username <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="main.html"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
                    <li>
                        <a href="eo-home.html"><i class="fa fa-fw fa-home"></i> Home</a>
                    </li>
                    <li>
                        <a href="javascript:;" data-toggle="collapse"
                           data-target="#demo">
                            <i class="fa fa-fw fa-table"></i>
                            Create<i class="fa fa-fw fa-caret-down"></i>
                        </a>
                        <ul id="demo" class="collapse">
                            <li>
                                <a href="add-election.html"> Election</a>
                            </li>
                            <li> <a href="add-issue.html"> Issue</a> </li>
                        </ul>
                    </li>
                    <li>
                        <a href="eo-validation.html"> Validate</a>
                    </li>
                    <li>
                        <a href="eo-view.html"> View Results</a>
                    </li>
                    <li>
                        <a href="ballot-search.html"> Ballot Search</a>
                    </li>
                    <li>
                        <a href="eo-home.html"> Cancel</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </nav>
        <div id="page-wrapper">
            <div class="container-fluid">
                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            Editing Ballot <br>
                        </h1>
                        <h4>
                            Editing Ballot NAME for district DIST<br>
                        </h4>
                    </div>
                </div>
                <!-- /.row -->
                <div class="row">
                    <div class="col-lg-6">
                        <form role="form">
                            <div class="form-group">
                                Ballot goes here with all items
                                editable, such as candidate info, party info, issues,
                                etc.<br>
                                <div class="form-group">
                                    <br>
                                    <br>
                                    <br>
                                    <br>
                                    <br>
                                    <br>
                                    <br>
                                </div>
                                <br>
                                <br>
                            </div>
                        </form>
                    </div>
                </div>
                <a href="Thanks.html">
                    <button type="submit" class="btn
btn-default">
                        Submit
                    </button>
                </a>
            </div>
            <!-- /.row -->
        </div>
        <!-- /.container-fluid -->
    </div>
    <!-- /#page-wrapper -->
    <!-- /#wrapper -->
    <!-- jQuery -->
    <script src="js/jquery.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
</body>
</html>
