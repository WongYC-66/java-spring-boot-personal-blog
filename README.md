### Built in JAVA
### part of learning project at https://roadmap.sh/projects/personal-blog

# Feature
1. Very basic blog-like feature
   - CRUD of blog
2. Very basic Authethentication with default username/password, with spring security
   - http://localhost:8080/  (non-authenticated)
   - http://localhost:8080/home (non-authenticated)
   - http://localhost:8080/article/:article_id (non-authenticated)
  
   - http://localhost:8080/admin (authenticated, authorized with ADMIN role)
   - http://localhost:8080/new (authenticated, authorized with ADMIN role)
   - http://localhost:8080/edit/:article_id (authenticated, authorized with ADMIN role)

3. Ignore UI.

# Prerequisite :
1. Install gradle , JAVA SDK

# Usage :
```
gradle build

gradle bootRun
```

1. access web app at http://localhost:8080/

1. login with username : admin, password : password

1. Example:
    ![image1](/image_home.png)
    ![image2](/image_login.png)
    ![image3](/image_edit.png)
    ![image4](/image_article.png)
