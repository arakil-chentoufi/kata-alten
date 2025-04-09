# 🛒 KataAlten API - Gestion de Produits

Ce projet est une API REST construite avec **Java + Spring Boot + Gradle**, permettant de gérer :
- des produits
- un panier d'achat
- une liste d'envie (wishlist)
- l'authentification via JWT
- des autorisations simples (admin@admin.com)

---

## 🚀 Lancer l'application

### ⚙️ Prérequis

- Java 17 ou plus
- Gradle (ou utilisez le wrapper `./gradlew`)

### ▶️ Démarrer l'application

```bash
# Cloner le projet
git clone https://github.com/arakil-chentoufi/kata-alten.git
cd kata-alten

# Lancer avec Gradle wrapper
./gradlew bootRun

# Créer un compte
POST /api-auth/register

{
  "username": "admin@admin.com",
  "firstname": "admin",
  "email": "admin@admin.com",
  "password": "admin1234"
}

# Se connecter
POST /api-auth/authenticate

{
  "username": "admin@admin.com",
  "password": "admin1234"
}
🧪 Documentation & Tests
Swagger (OpenAPI UI)
📘 http://localhost:8080/swagger-ui.html

ou : http://localhost:8080/swagger-ui/index.html


