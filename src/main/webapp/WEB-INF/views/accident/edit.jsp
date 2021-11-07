<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

    <title>car accident</title>

    <script>
        function validate() {
            const answer = "Пожалуйста заполните ";
            let result = answer;
            if ($('#nameId').val() === '') {
                result += "-Название ";
            }
            if ($('#textId').val() === '') {
                result += "-Причину ";
            }
            if ($('#addressId').val() === '') {
                result += "-Адрес ";
            }
            if ($("#selectRuleId option:selected").val() === undefined) {
                result += "-Статьи";
            }
            if (answer !== result) {
                alert(result);
                return false;
            }
            return true;
        }
    </script>
</head>
<body>

<form  action="<c:url value='/save'/>" method='POST'>
    <table>
        <tr>
            <td><input type='hidden' name='id' value="<c:out value="${accident.id}"/>"></td>
        </tr>
        <tr>
            <td>Тип:</td>
            <td>
                <select name="type.id">
                    <c:forEach var="type" items="${types}">
                        <option value="${type.id}">${type.name}</option>
                    </c:forEach>
                </select>
            <td>
        </tr>
        <tr>
            <td>Название:</td>
            <td><input type='text' id="nameId" name='name' value="<c:out value="${accident.name}"/>"></td>
        </tr>
        <tr>
            <td>Причина:</td>
            <td><input type='text' id="textId" name='text' value="<c:out value="${accident.text}"/>"></td>
        </tr>
        <tr>
            <td>Адрес:</td>
            <td><input type='text' id="addressId" name='address' value="<c:out value="${accident.address}"/>"></td>
        </tr>
        <tr>
            <td>Статьи</td>
            <td><select id="selectRuleId" name="rIds" multiple>
                <c:forEach var="rule" items="${rules}" >
                    <option value="${rule.id}">${rule.name}</option>
                </c:forEach>
            </select></td>
        </tr>
        <tr>
            <td colspan='2'><input name="submit" type="submit" onclick="return validate();" value="Сохранить" /></td>
        </tr>
    </table>
</form>
</body>
</html>