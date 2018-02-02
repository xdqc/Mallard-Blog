<head>
    <%--jquery--%>
    <script src="https://code.jquery.com/jquery-3.3.1.js"
            integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
            crossorigin="anonymous"></script>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>

    <%--fontawsome-icons--%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
          integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">

    <%--jquery-timeago--%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-timeago/1.6.1/jquery.timeago.js"
            integrity="sha256-eAM7NHrUqBdkWBYcUVaGo55W9YMG8UAQIceYOf9xnjg=" crossorigin="anonymous"></script>

    <%--sweet-alert--%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert-dev.js"
            integrity="sha256-Y6AEuvugryve80FgzPE3e2EONgfiYPcaSaqF+vIFGIA=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.css"
          integrity="sha256-k66BSDvi6XBdtM2RH6QQvCz2wk81XcWsiZ3kn6uFTmM=" crossorigin="anonymous"/>

        <%--table-sorter--%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.tablesorter/2.29.5/js/jquery.tablesorter.js" integrity="sha256-z8DJfiCHX/POWVqa6jM3xdqizgSoHMQZeb1Ri4FzM3A=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.tablesorter/2.29.5/js/parsers/parser-date.min.js" integrity="sha256-tKA6ehiote08tAMlvjaz2u2xKRjMmtA9tnZ0hE4/MMU=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.tablesorter/2.29.5/js/parsers/parser-date-range.min.js" integrity="sha256-HsOOagtQtwyP0U5GuuHMR0KF8W2bfU/CMg5zsAGE/60=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/mark.js/8.11.1/jquery.mark.js" integrity="sha256-m4GLhtBF1Ue31vdmii9AEzvSYnBTJFzYkVToaD047Z4=" crossorigin="anonymous"></script>
    <script src="https://cdn.datatables.net/plug-ins/1.10.16/features/mark.js/datatables.mark.js"></script>

    <script type="text/javascript" src="javascript/moment-with-locales.js"></script>

    <script type="text/javascript">
        let loggedInUser = ${sessionScope.get('loggedInUser')==null?0:sessionScope.get('loggedInUser').getId()};
    </script>




        <!-- Form validator -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/1000hz-bootstrap-validator/0.11.9/validator.min.js"></script>

    <!-- For upload files page generate multiple row -->
    <script type="text/javascript" src="javascript/uploadFile.js"></script>
    <script type="text/javascript" src="javascript/personal_blog.js"></script>
    <script type="text/javascript" src="javascript/homepage.js"></script>
    <script type="text/javascript" src="javascript/login.js"></script>


        <!-- PUT CSS HERE -->
    <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
    <link rel="stylesheet" href="css/article.css"/>
    <link rel="stylesheet" href="css/homepage_menu.css"/>
    <link rel="stylesheet" href="css/personal_panel.css">

</head>
