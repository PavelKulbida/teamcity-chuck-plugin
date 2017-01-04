<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="happyImage" scope="request" type="java.lang.String"/>
<jsp:useBean id="sadImage" scope="request" type="java.lang.String"/>
<jsp:useBean id="chuckHappy" scope="request" type="java.lang.Boolean"/>
<jsp:useBean id="message" scope="request" type="java.lang.String"/>
<jsp:useBean id="existMusic" scope="request" type="java.lang.Boolean"/>
<jsp:useBean id="music" scope="request" type="java.lang.String"/>

<div class="chuck_div" style="padding: 5px; display: inline-block;">
    <div class="chuck_div_image">
        <c:choose>
          <c:when test="${chuckHappy}">
                <img style="float: left; padding-right: 10px;" src="<c:url value="${happyImage}"/>"/>
          </c:when>
          <c:otherwise>
                <img style="float: left; padding-right: 10px;" src="<c:url value="${sadImage}"/>"/>
          </c:otherwise>
        </c:choose>
    </div>

    <c:choose>
      <c:when test="${existMusic}">
        <div class="chuck_div_music">
            <audio src="<c:url value="${music}"/>" controls>
                <embed width="180" height="30" type="audio/mpeg" loop="false" autostart="0" hidden="false" src="<c:url value="${music}"/>"/>
            </audio>
        </div>
        <div style="font-size:0; display:block; line-height:0;">
            &nbsp;
        </div>
        <div style="font-size:0; display:block; line-height:0;">
            &nbsp;
        </div>
      </c:when>
      <c:otherwise>
      </c:otherwise>
    </c:choose>

    <span class="chuck_text">
        <h2>${message}</h2>
    </span>

</div>
