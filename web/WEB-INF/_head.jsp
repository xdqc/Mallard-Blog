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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.20.1/locale/en-nz.js" integrity="sha256-mlQNJjxrjuiJvrKTfLRsEGg6cTWQJ6sqwMEMyiM10Ws=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.20.1/moment-with-locales.min.js" integrity="sha256-XWrGUqSiENmD8bL+BVeLl7iCfhs+pkPyIqrZQcS2Te8=" crossorigin="anonymous"></script>

    <%--sweet-alert--%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert-dev.js"
            integrity="sha256-Y6AEuvugryve80FgzPE3e2EONgfiYPcaSaqF+vIFGIA=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.css"
          integrity="sha256-k66BSDvi6XBdtM2RH6QQvCz2wk81XcWsiZ3kn6uFTmM=" crossorigin="anonymous"/>

        <%--table-sorter--%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.tablesorter/2.29.5/js/jquery.tablesorter.js" integrity="sha256-z8DJfiCHX/POWVqa6jM3xdqizgSoHMQZeb1Ri4FzM3A=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/mark.js/8.11.1/jquery.mark.js" integrity="sha256-m4GLhtBF1Ue31vdmii9AEzvSYnBTJFzYkVToaD047Z4=" crossorigin="anonymous"></script>
    <script src="https://cdn.datatables.net/plug-ins/1.10.16/features/mark.js/datatables.mark.js"></script>

    <script type="text/javascript" src="javascript/moment-with-locales.js"></script>

    <script type="text/javascript">
        /*do not delete */
        let loggedInUser = ${sessionScope.get('loggedInUser')==null?0:sessionScope.get('loggedInUser').getId()};
    </script>

        <!-- Form validator -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/1000hz-bootstrap-validator/0.11.9/validator.min.js"></script>

    <!-- For upload files page generate multiple row -->
    <script type="text/javascript" src="javascript/uploadFile.js"></script>
    <script type="text/javascript" src="javascript/personal_blog.js"></script>
    <script type="text/javascript" src="javascript/login.js"></script>
    <script type="text/javascript" src="javascript/admin.js"></script>
    <script type="text/javascript" src="javascript/homepage.js"></script>

        <!-- PUT CSS HERE -->
    <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
    <link rel="stylesheet" href="css/article.css"/>
    <link rel="stylesheet" href="css/homepage_menu.css"/>
    <link rel="stylesheet" href="css/personal_panel.css">
    <link rel="stylesheet" href="css/footer.css"/>
    <link rel="stylesheet" href="css/homepage_profile.css">

        <%--favicon--%>
        <link rel="apple-touch-icon" sizes="57x57" href="pictures/favicon/apple-icon-57x57.png">
        <link rel="apple-touch-icon" sizes="60x60" href="pictures/favicon/apple-icon-60x60.png">
        <link rel="apple-touch-icon" sizes="72x72" href="pictures/favicon/apple-icon-72x72.png">
        <link rel="apple-touch-icon" sizes="76x76" href="pictures/favicon/apple-icon-76x76.png">
        <link rel="apple-touch-icon" sizes="114x114" href="pictures/favicon/apple-icon-114x114.png">
        <link rel="apple-touch-icon" sizes="120x120" href="pictures/favicon/apple-icon-120x120.png">
        <link rel="apple-touch-icon" sizes="144x144" href="pictures/favicon/apple-icon-144x144.png">
        <link rel="apple-touch-icon" sizes="152x152" href="pictures/favicon/apple-icon-152x152.png">
        <link rel="apple-touch-icon" sizes="180x180" href="pictures/favicon/apple-icon-180x180.png">
        <link rel="icon" type="image/png" sizes="192x192"  href="pictures/favicon/android-icon-192x192.png">
        <link rel="icon" type="image/png" sizes="32x32" href="pictures/favicon/favicon-32x32.png">
        <link rel="icon" type="image/png" sizes="96x96" href="pictures/favicon/favicon-96x96.png">
        <link rel="icon" type="image/png" sizes="16x16" href="pictures/favicon/favicon-16x16.png">
        <link rel="shortcut icon" href="pictures/favicon/favicon-32x32.png" type="image/x-icon">

        <link rel="manifest" href="pictures/favicon/manifest.json">
        <meta name="msapplication-TileColor" content="#ffffff">
        <meta name="msapplication-TileImage" content="/ms-icon-144x144.png">
        <meta name="theme-color" content="#ffffff">

</head>
