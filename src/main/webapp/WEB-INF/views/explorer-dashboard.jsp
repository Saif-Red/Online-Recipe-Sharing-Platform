<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0"><title>My Collection - RecipeHub</title><link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"></head>
<body>
<%@ include file="partials/header.jsp" %>
<main class="page-shell">
    <section class="hero-row"><div><p class="eyebrow">EXPLORER DASHBOARD</p><h1>Your recipe shelf.</h1><p class="muted">Keep favourites organised and see what you explored recently.</p></div><span class="pill">12 saved recipes</span></section>
    <section class="dashboard-two-col"><div class="table-section"><div class="section-head">
                                                                      <div>
                                                                          <h2>Recipe collection</h2>
                                                                          <p class="muted small">Quick access to saved ideas.</p>
                                                                      </div>

                                                                      <a
                                                                              href="${pageContext.request.contextPath}/collections"
                                                                              class="btn secondary"
                                                                      >
                                                                          View my collections
                                                                      </a>
                                                                  </div><div class="collection-grid">

                                                                            <a
                                                                                    href="${pageContext.request.contextPath}/collections"
                                                                                    class="collection-card"
                                                                            >
                                                                                <span>🍝</span>
                                                                                <strong>Weeknight Favourites</strong>
                                                                                <small>View your saved collections</small>
                                                                            </a>

                                                                            <a
                                                                                    href="${pageContext.request.contextPath}/collections"
                                                                                    class="collection-card"
                                                                            >
                                                                                <span>🍰</span>
                                                                                <strong>Dessert Ideas</strong>
                                                                                <small>View your saved collections</small>
                                                                            </a>

                                                                        </div></div><div class="side-card"><p class="eyebrow">BROWSING HISTORY</p><h3>Recently viewed</h3><p class="muted">Creamy Garlic Pasta · Today</p><p class="muted">Masala Vegetable Toast · Yesterday</p><p class="muted">Chocolate Banana Mug Cake · Sep 30</p></div></section>
    <section class="table-section"><div class="section-head"><div><h2>Your reviews</h2><p class="muted small">Ratings and feedback you have shared.</p></div></div><div class="simple-list"><div><strong>Creamy Garlic Pasta</strong><span>★★★★★ · “Very easy to make.”</span></div><div><strong>Masala Vegetable Toast</strong><span>★★★★☆ · “Crispy and tasty.”</span></div></div></section>
</main>
<%@ include file="partials/footer.jsp" %>
</body>
</html>
