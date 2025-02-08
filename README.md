# desafio-itau-junior

## Documentação do Projeto
### Instalação
1. Instalando o Docker e o Docker Compose
Para rodar este projeto, você precisa ter o Docker e o Docker Compose instalados na sua máquina. Siga os passos abaixo para instalar as ferramentas:

Docker
Acesse o site oficial do Docker para baixar e instalar o Docker: https://docs.docker.com/engine/install/

Baixe o instalador adequado para o seu sistema operacional (Windows, macOS ou Linux).

Siga as instruções do instalador.

Docker Compose

O Docker Compose já vem instalado junto com o Docker em sistemas Linux e Windows. Caso contrário, você pode seguir as instruções de instalação no site oficial do Docker Compose.
Usando o Docker Compose
2. Compilando o Projeto com Docker Compose
Após instalar o Docker e o Docker Compose, você pode compilar o projeto executando o comando abaixo:

```bash
docker-compose up --build
```
Este comando irá construir as imagens Docker do projeto e iniciar os containers definidos no docker-compose.yml.

caso nao queira usar o docker-compose, pode rodar o comando abaixo para compilar e executar o projeto:
```bash
mvn clean package
java -jar petlocation-0.0.1-SNAPSHOT.jar
```

### Arquitetura do Projeto
3. Arquitetura Hexagonal
A arquitetura utilizada neste projeto segue o Padrão Hexagonal (Ports and Adapters). Esse padrão visa isolar a lógica de negócios do resto do sistema, permitindo que você conecte diferentes partes da aplicação de maneira mais flexível. A arquitetura permite:

Acoplamento baixo: As dependências entre os componentes são mínimas, com a lógica de negócios isolada de detalhes de infraestrutura, como acesso a bancos de dados, APIs externas e sistemas de mensageria.
Extensibilidade: A aplicação pode ser facilmente estendida com novos adaptadores ou portas, sem a necessidade de modificar a lógica central.
Testabilidade: Como a lógica de negócios é isolada, os testes de unidade podem ser realizados sem a necessidade de interagir com partes externas.
Essa arquitetura me permite integrar diferentes componentes (como Prometheus e sistemas de logging) sem comprometer a lógica de negócios.

### Cobertura de Testes
4. Testes de Unidade e Integração
Consegui cobrir todo o código com testes. Para garantir a qualidade do código, foram escritos testes de unidade para as partes da aplicação que não dependem de recursos externos e testes de integração para verificar a integração de componentes como o banco de dados e APIs externas.

Cobertura de Código (Code Coverage)

A cobertura de testes foi configurada para garantir que todas as funcionalidades estejam sendo testadas. O código foi coberto por testes para que a aplicação seja robusta e menos propensa a erros em produção.

### Padrões de Desenvolvimento
5. SOLID e Clean Code
Durante o desenvolvimento, busquei aplicar os princípios do SOLID e Clean Code para garantir que o código fosse bem estruturado, fácil de entender e de manter:

SOLID: Apliquei os princípios SOLID para garantir que a aplicação fosse modular, reutilizável e fácil de estender.
Clean Code: O código foi escrito de maneira limpa e legível, com foco na simplicidade e clareza.
### Integração com Prometheus
6. Monitoramento com Prometheus
O projeto foi configurado para expor métricas para o Prometheus. No arquivo docker-compose.yml, configurei um serviço de Prometheus que se conecta ao endpoint de métricas da aplicação. Isso permite que a aplicação seja monitorada e as métricas sejam coletadas e analisadas.

Ao rodar o comando docker-compose up, o Prometheus também será iniciado, configurado para coletar métricas da aplicação.

### Logs e Observabilidade
7. Logs e Métricas
Logs foram inseridos em pontos importantes do código para permitir a observabilidade. Isso ajuda a monitorar o comportamento da aplicação e identificar potenciais problemas. Além disso, as métricas expostas para o Prometheus fornecem informações sobre o desempenho da aplicação.

8. Método POST no lugar de GET
Optei por utilizar o método POST no lugar de GET para a rota /pet-location/address, pois no POST é possível enviar informações no corpo da requisição (como o endereço do pet e sua localização), o que não seria possível no GET, que limita os dados a serem passados via parâmetros de URL. O método POST oferece maior flexibilidade para enviar dados de forma segura e estruturada.

Como Usar a API
Para interagir com a API, envie um POST para a seguinte URL:

```bash
curl -X POST http://localhost:8080/pet-location/address \
  -H "Content-Type: application/json" \
  -d '{
    "sensorId": "123",
    "latitude": -23.5505,
    "longitude": -46.6333,
    "timestamp": "2023-10-01T12:34:56"
  }'
```
POST http://localhost:8080/pet-location/address

O corpo da requisição deve conter os dados do pet, incluindo sua localização.
### Considerações Finais

A Arquitetura Hexagonal me proporcionou um alto nível de desacoplamento, permitindo a extensibilidade da aplicação. Isso significa que eu posso facilmente adicionar novos componentes ou adaptar a aplicação para novos requisitos sem afetar a lógica central. Essa abordagem também facilita a manutenção, pois cada parte do sistema é bem definida e isolada.