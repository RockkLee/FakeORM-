<%@page import="sheng.orm.test.Testing"%>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
</head>
<body>
    <h1><%= new Testing().print("Hello World") %></h1>
</body>
</html>