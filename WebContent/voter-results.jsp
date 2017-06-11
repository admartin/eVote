﻿<!DOCTYPE html>
<html lang="en">
<head>
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
    <!-- Morris Charts CSS -->
    <link href="css/plugins/morris.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
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
                <a class="navbar-brand" href="voter-home.html">eVote</a>
            </div>
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> Username <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="voter-profile.html"><i class="fa fa-fw fa-user"></i> Profile</a>
                        </li>
                        <li>
                            <a href="voter-settings.html"><i class="fa fa-fw fa-gear"></i> Settings</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="main.html"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
                    <li class="active">
                        <a href="voter-home.html"><i class="fa fa-fw fa-home"></i> Home</a>
                    <li>
                        <a href="voter-ballot.html"><i class="fa fa-fw fa-edit"></i> Vote</a>
                    </li>
                    <li>
                        <a href="javascript:;" data-toggle="collapse" data-target="#demo"><i class="fa fa-fw fa-table"></i> Results<i class="fa fa-fw fa-caret-down"></i></a>
                        <ul id="demo" class="collapse">
                            <li>
                                <a href="results.html"> Spring 2016 Primaries</a>
                            </li>
                            <li>
                                <a href="results.html"> Fall 2016 Election</a>
                            </li>
                        </ul>
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
                            Finalized Results
                        </h1>
                    </div>
                </div>
                <!-- /.row -->
                <div class="row">
                    <div class="col-sm-6">
                        <label>Mayor</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-offset-1 col-sm-6">
                        <table class="table">
                            <tr>
                                <td><strong>#</strong></td>
                                <td><strong>Name</strong></td>
                                <td><strong>Party</strong></td>
                            </tr>
                            <tr>
                                <td>1</td>
                                <td>Will Smith</td>
                                <td>Democrat</td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>Julie Andrews</td>
                                <td>Republican</td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6">
                        <label>District 12 House Representative</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-offset-1 col-sm-6">
                        <table class="table">
                            <tr>
                                <td><strong>#</strong></td>
                                <td><strong>Name</strong></td>
                                <td><strong>Party</strong></td>
                            </tr>
                            <tr>
                                <td>1</td>
                                <td>Colonel Sanders</td>
                                <td>Republican</td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>Ronald McDonald</td>
                                <td>Democrat</td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12">
                        <label>Judge</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-offset-1 col-sm-6">
                        <table class="table">
                            <tr>
                                <td><strong>#</strong></td>
                                <td><strong>Name</strong></td>
                                <td><strong>Party</strong></td>
                            </tr>
                            <tr>
                                <td>1</td>
                                <td>Judith Sheindlin</td>
                                <td>Democrat</td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>Greg Mathis</td>
                                <td>Republican</td>
                            </tr>
                        </table>
                    </div>
                </div>
                    </div>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->
    <!-- jQuery -->
    <script src="js/jquery.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
    <!-- Morris Charts JavaScript -->
    <script src="js/plugins/morris/raphael.min.js"></script>
    <script src="js/plugins/morris/morris.min.js"></script>
    <script src="js/plugins/morris/morris-data.js"></script>
</body>
</html>
