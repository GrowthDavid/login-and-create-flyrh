# Porta: 8080

O sistema foi testado.

# Post:

localhost:8080/access/create-first-user -- Cadastro do primeiro usuario (sem proteção)

localhost:8080/auth/login -- Acesso do login

localhost:8080/users/{id} -- para update, atualizar dados, delete

localhost:8080/users -- GET Busca todos os usuarios no banco

localhost:8080/users -- Post Cria usuario como administrador, basta usar template abaixo

# Autenticator

Autenticação realizada por token de acesso, você obtem atraves do login de um usuario criado com roles admin (Bearer Token).
O erro de sem autorização é devido a falta do token ou token incorreto, você irá receber após criar o usuario e realizar um login no mesmo.

{

    "name": "Admin",
    
    "email": "growth@example.com",
    
    "password": "ggadmin",
    
    "role": "admin"
    
}

