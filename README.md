# API Adopet - Checkpoint: DevOps Tools & Cloud Computing

## Sobre o projeto

Este projeto é uma API REST desenvolvida em Java com Spring Boot para o sistema de adoção de pets "Adopet".  
A API implementa um CRUD completo para as entidades de Abrigos, Tutores e Pets, além de gerenciar o processo de adoção.

---

## Integrantes:
- Hassan Chahine - RM556715
- Guilherme Cardoso dos Santos - RM555178
- João Pedro Motta Marcolini - RM556557

## Tecnologias Utilizadas

As seguintes tecnologias foram utilizadas no desenvolvimento e implantação do projeto:

- **Backend:** Java 17, Spring Boot 3
- **Banco de Dados:** Azure SQL Server (PaaS)
- **Build e Dependências:** Maven
- **Migrações de Banco:** Flyway
- **Cloud Provider:** Microsoft Azure
- **Serviços Azure Utilizados:**
  - Azure App Service (Web App)
  - Azure SQL Database
  - Application Insights

---

## Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina:
- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven 3.x](https://maven.apache.org/download.cgi)
- IntelliJ IDEA
- [Git](https://git-scm.com/downloads)
- Uma conta ativa na [Microsoft Azure](https://azure.microsoft.com/)

---

## How-to: Implantação do Projeto na Nuvem

Este guia detalha o processo para configurar os recursos no Azure e realizar o deploy da aplicação utilizando o Azure Toolkit no IntelliJ IDEA.

### Passo 1: Configuração dos Recursos no `Portal Azure`

Antes de fazer o deploy, é necessário criar os seguintes recursos no portal do Azure:

1.  **Grupo de Recursos:** Crie um novo grupo de recursos para organizar todos os serviços do projeto.
2.  **Servidor SQL e Banco de Dados:**
    * Crie um **Servidor SQL do Azure**.
    * Dentro do servidor, crie um **Banco de Dados SQL** (ex: `adopet-db`). Anote o nome do servidor, o nome do banco, o usuário e a senha do administrador.
    * No menu do Servidor SQL, vá em **Rede** e marque a opção **"Permitir que os serviços e recursos do Azure acessem este servidor"**.
3.  **Application Insights:**
    * Crie um recurso do **Application Insights** para monitoramento.
    * Copie a **"Cadeia de Conexão"** (Connection String) deste recurso.

### Passo 2: Configuração do Projeto Local

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/hachahine/cp05_devops.git
    cd adopet-api
    ```
2.  **Configure o `application.properties`:**
    * Abra o arquivo `application.properties`.
    * Preencha as credenciais do seu banco de dados Azure SQL e a cadeia de conexão do Application Insights:
    ```properties
    # sql server conn
    spring.datasource.url=jdbc:sqlserver://<servidor>.database.windows.net:1433;database=<database>...
    spring.datasource.username=<seu-usuario>@<servidor>
    spring.datasource.password=<sua-senha>
    spring.jpa.hibernate.dialect=org.hibernate.dialect.SQLServerDialect

    # Permite que o Flyway gerencie um BD não-vazio
    spring.flyway.baseline-on-migrate=true

    # Application Insights
    azure.application-insights.connection-string=<Sua_Connection_String_do_Application_Insights>
    ```

### Passo 3: Deploy via IntelliJ IDEA

1.  **Instale o Azure Toolkit:** No IntelliJ, vá em `File > Settings > Plugins` e instale o "Azure Toolkit for IntelliJ".
2.  **Login:** Faça login na sua conta Azure através da nova aba lateral "Azure" que aparecerá na IDE.
   <img width="869" height="847" alt="unnamed" src="https://github.com/user-attachments/assets/f6d8a775-5325-4fb2-ba3c-14f24b296f8a" />

4.  **Inicie o Deploy:** Clique com o botão direito no projeto e vá em **Azure -> Deploy to Azure Web Apps...**.
   <img width="919" height="854" alt="unnamed (1)" src="https://github.com/user-attachments/assets/f60942bd-be0d-40f5-a31a-3f7fd5b24720" />

6.  **Configure o Web App:**
    * Na seção **App**, clique no `+` para criar um novo App Service.
    * **Name:** Dê um nome único global (ex: `adopet-api-cp2`).
    * **Platform:** `Linux`.
    * **Runtime:** Selecione **Java 17**.
      <img width="1194" height="862" alt="unnamed (2)" src="https://github.com/user-attachments/assets/08fafe60-6caf-45b0-86ad-5416fe3ce665" />

7.  **Configure o Artefato:**
    * Na seção **Artifact**, certifique-se de que o artefato selecionado é o arquivo `.jar` do projeto (ex: `adopet-api-0.0.1-SNAPSHOT.jar`).
8.  **Execute o Deploy:** Clique em **Run**. O IntelliJ irá criar os recursos e publicar a aplicação. A URL final será algo como `https://<nome-do-app>.azurewebsites.net`.
   <img width="1062" height="673" alt="image" src="https://github.com/user-attachments/assets/5522a2c7-e4d4-4a07-98b6-9a7e67a42598" />


### Passo 4: Validação

1.  **Teste os Endpoints:** Utilize Postman/Insomnia para testar as rotas da sua API usando a URL do Azure (ex: `GET https://<nome-do-app>.azurewebsites.net/abrigos`).
2.  **Verifique o Banco:** Acesse o **Editor de Consultas** do seu Banco SQL no portal Azure e execute `SELECT * FROM abrigos;` para confirmar que os dados foram persistidos.
3.  **Monitore a Aplicação:** Acesse o recurso do **Application Insights** no portal e explore a aba **Live Metrics** para ver as requisições chegando em tempo real.

---

## API Endpoints e Exemplos de JSON

A seguir, exemplos das operações CRUD para cada entidade.

### Abrigos (`/abrigos`)
* `POST /abrigos` - Cadastra um abrigo.
    ```json
    {
    "nome": "Abrigo Céu Azul",
    "telefone": "(11)91111-2222",
    "email": "contato@ceuazul.com"
    }
    ```
* `GET /abrigos` - Lista todos os abrigos.
* `PUT /abrigos` - Atualiza um abrigo.
    ```json
    {
    "id": 1,
    "nome": "Novo Nome Abrigo",
    "telefone": "(11)93333-4444",
    "email": "novo@email.com"
    }
    ```
* `DELETE /abrigos/{id}` - Deleta um abrigo.

### Tutores (`/tutores`)
* `POST /tutores` - Cadastra um tutor.
    ```json
    {
    "nome": "Maria Souza",
    "telefone": "(21)98888-7777",
    "email": "maria.souza@email.com"
    }
    ```
* `GET /tutores` - Lista todos os tutores.
* `PUT /tutores` - Atualiza um tutor.
    ```json
    {
    "id": 1,
    "nome": "Maria Souza da Silva",
    "telefone": "(21)95555-6666",
    "email": "maria.silva@email.com"
    }
    ```
* `DELETE /tutores/{id}` - Deleta um tutor.

### Pets (`/pets`)
* `POST /abrigos/{abrigoId}/pets` - Cadastra um pet em um abrigo.
    ```json
    {
    "tipo": "CACHORRO",
    "nome": "Bolinha",
    "raca": "Poodle",
    "idade": 5,
    "cor": "Branco",
    "peso": 8.5
    }
    ```
* `GET /pets` - Lista todos os pets disponíveis para adoção.
* `PUT /pets` - Atualiza um pet.
    ```json
    {
    "id": 1,
    "nome": "Bolinha Feliz",
    "raca": "Poodle",
    "idade": 6,
    "cor":"Branco",
    "peso": 8.7
    }
    ```
* `DELETE /pets/{id}` - Deleta um pet.

