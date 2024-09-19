# Porta: 8080

O sistema foi testado.

# Post:

localhost:8080/access/create-first-user -- Cadastro do primeiro usuario (sem proteção)
localhost:8080/auth/login -- Acesso do login
localhost:8080/users/{id} -- para update, atualizar dados, delete
localhost:8080/users -- GET

# Autenticator

Autenticação realizada por token de acesso, você obtem atraves do login de um usuario criado com roles admin.

{
    "name": "Admin",
    "email": "growth@example.com",
    "password": "ggadmin",
    "role": "admin"
}

