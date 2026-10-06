<%@ page import="com.recipehub.model.User" %>
<%@ page import="com.recipehub.model.UserRole" %>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
%>
<header class="topbar">
    <a class="brand" href="${pageContext.request.contextPath}/feed">RecipeHub</a>

    <form class="top-search" action="${pageContext.request.contextPath}/feed" method="get">
        <input
                type="search"
                name="q"
                value="<%= request.getParameter("q") == null ? "" : request.getParameter("q") %>"
                placeholder="Search recipes, cuisines, ingredients..."
        >
    </form>
    <nav class="top-nav">
        <a href="${pageContext.request.contextPath}/feed">Explore</a>
        <% if (loggedInUser != null && loggedInUser.getRole() == UserRole.CONTRIBUTOR) { %>
            <a href="${pageContext.request.contextPath}/contributor/dashboard">My kitchen</a>
        <% } %>
        <% if (loggedInUser != null && loggedInUser.getRole() == UserRole.EXPLORER) { %>
            <a href="${pageContext.request.contextPath}/explorer/dashboard">My collection</a>
        <% } %>
        <% if (loggedInUser != null && loggedInUser.getRole() == UserRole.ADMIN) { %>
            <a href="${pageContext.request.contextPath}/admin/dashboard">Admin</a>
        <% } %>
    </nav>
    <div class="profile-mini">
        <span class="avatar"><%= loggedInUser == null ? "?" : loggedInUser.getName().substring(0, 1).toUpperCase() %></span>
        <div>
            <strong><%= loggedInUser == null ? "Guest" : loggedInUser.getName() %></strong>
            <small><%= loggedInUser == null ? "" : loggedInUser.getRole().name().toLowerCase() %></small>
        </div>
        <a class="logout" href="${pageContext.request.contextPath}/logout">Log out</a>
    </div>
</header>
