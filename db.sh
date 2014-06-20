#!/bin/bash
NOME=$USER
echo 'Instalação e configuração do banco de dados local'
echo '-------------------------------------------------'
# Baixa e instala postgreSQL
sudo apt-get update
sudo apt-get install postgresql
# Cria um arquivo com comandos para user postgres
touch postgres_commands
echo 'createuser '$NOME > postgres_commands
echo 'createdb '$NOME >> postgres_commands
echo 'exit' >> postgres_commands
# Cria um arquivo com comandos para psql
touch change_password
echo '\password' > change_password
echo '\q' >> change_password
# Executa os comandos como user postgres
cat ./postgres_commands | sudo -u postgres -i
echo '==========================='
echo 'Utilize como password: pass'
echo '==========================='
# Executa os comandos no psql
cat ./change_password | psql
# Adiciona variáveis de ambiente no .bashrc
echo 'export DATABASE_URL="jdbc:postgresql://localhost:5432/'$NOME'?user='$NOME'&password=pass"' >> ~/.bashrc
echo 'export URL_ENVIRONMENT="http://localhost:3333"' >> ~/.bashrc
# Remove arquivos temporários
rm postgres_commands
rm change_password
echo 'OK'

