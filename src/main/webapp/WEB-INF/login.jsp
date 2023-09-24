<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>

<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title></title>


</head>

<body>

    <h1>Hello World</h1>
    <input type="text" name="${_csrf.parameterName}" value="${_csrf.token}" />

</body>

</html>