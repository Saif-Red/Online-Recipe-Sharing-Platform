<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>RecipeHub - Sign in</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="auth-page">
<div class="auth-shell">
    <section class="auth-art">
        <div class="brand-mark">🍴 RecipeHub</div>
        <div>
            <p class="eyebrow">SHARE. DISCOVER. COOK.</p>
            <h1>Good recipes deserve<br>good company.</h1>
            <p class="muted-light">A simple community for home cooks, recipe explorers and the people who keep the kitchen interesting.</p>
        </div>
        <div class="mini-stat-row">
            <span><strong>3</strong> user roles</span>
            <span><strong>1</strong> recipe community</span>
        </div>
    </section>
    <section class="auth-card-wrap">
        <div class="auth-card">
            <p class="eyebrow">WELCOME BACK</p>
            <h2>Sign in to RecipeHub</h2>
            <p class="muted">Use one of the demo accounts for the review build.</p>
            <% if (request.getAttribute("error") != null) { %>
                <div class="alert error"><%= request.getAttribute("error") %></div>
            <% } %>
            <form action="${pageContext.request.contextPath}/login" method="post" class="stack-form">
                <label>Email
                    <input type="email" name="email" required placeholder="you@example.com">
                </label>
                <label>Password
                    <input type="password" name="password" required placeholder="••••••••">
                </label>
                <button type="submit" class="btn primary full">Sign in</button>
            </form>
            <div class="demo-box">
                <strong>Demo accounts</strong>
                <span>Admin: admin@recipehub.local / admin123</span>
                <span>Contributor: meera@recipehub.local / cook123</span>
                <span>Explorer: rohan@recipehub.local / explorer123</span>
            </div>
        </div>
    </section>
</div>
</body>
</html>
