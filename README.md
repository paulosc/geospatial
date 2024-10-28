## Instruções para Executar o Projeto Geospatial
Siga os passos abaixo para realizar o checkout do projeto Geospatial e executar os testes unitários.

### 1. Clonando o Repositório
Primeiro, clone o repositório usando o seguinte comando:

***git clone https://github.com/paulosc/geospatial.git***
### 2. Navegando para o Diretório do Projeto
Acesse a pasta do projeto:

***cd geospatial***
### 3. Construindo a Imagem Docker
Certifique-se de que o Docker esteja em execução e, em seguida, construa a imagem do projeto com o comando:

***docker build -t geospatial .***
### 4. Executando o Contêiner
Após a construção da imagem, inicie o contêiner com o seguinte comando:

***docker run -p 8082:8082 geospatial***
### 5. Importando a Coleção Postman
Implemente todos os endpoints importando o arquivo Geospatial.postman_collection.json no Postman.

### 6. Executando os Testes Unitários
Os testes unitários utilizam os seguintes valores para validar o código:

Exemplo de Dados:

Id: 1
Nome: José da Silva
Data de Nascimento: 06/04/2000
Data de Admissão: 10/05/2020
Data Atual: 07/02/2023
Salário Mínimo Atual: R$1302,00
Respostas Esperadas:

Idade (em dias): 8342
Endpoint: /person/{id}/age?output=days
Idade (em meses): 274
Endpoint: /person/{id}/age?output=months
Idade (em anos): 22
Endpoint: /person/{id}/age?output=years
Salário (valor total): 3259,36
Endpoint: /person/{id}/salary?output=full
Salário (mínimo): 2,51
Endpoint: /person/{id}/salary?output=min
***Observação: Os cálculos nos testes utilizam a data atual como referência.***

***Projeto Geospatial***
Este projeto utiliza Spring Boot para fornecer uma API REST que manipula dados de geolocalização, oferecendo operações CRUD para a entidade Person.

***Estrutura de Testes***
Para garantir a qualidade e a consistência dos resultados, o projeto inclui:

***Collection do Postman***
Uma collection do Postman está disponível na raiz do repositório para facilitar os testes da API. Com ela, é possível realizar requisições para todos os endpoints e verificar os retornos esperados. Para utilizar a collection, importe-a diretamente no Postman e ajuste a URL base para o ambiente desejado.

***Testes Unitários***
Os testes unitários estão configurados para utilizar a data fixa de 07/02/2023 como referência. Isso é feito para garantir que os resultados permaneçam consistentes conforme descrito nas especificações de cada teste. Essa abordagem permite simular condições e calcular resultados baseados na data mencionada, independentemente da data em que os testes sejam executados.
