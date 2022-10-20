<%@ tag import="java.util.Base64" %>
<%@ tag import="java.nio.file.Files" %>
<%@ tag import="java.nio.file.Paths" %>
<%@ tag import="java.io.File" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ attribute name="image_name" required="true" type="java.lang.String"%>

<%
    String titleImage;
    String imagesFolder = application.getInitParameter("imagesFolder");
    assert imagesFolder != null : "Check 'imagesFolder' parameter in web.xml. ";
    String fullImagePath = imagesFolder + image_name;
    File f = new File(fullImagePath);
    if (!f.exists() || f.isDirectory()) {
        String defaultImageName = application.getInitParameter("defaultTitleImagePath");
        titleImage = request.getContextPath() + defaultImageName;
    } else {
        String encodedImage = new String(Base64.getEncoder().encode(Files.readAllBytes(Paths.get(fullImagePath))));
        titleImage = "data:image/jpeg;base64," + encodedImage;
    }

%>
<%=
    titleImage
%>
