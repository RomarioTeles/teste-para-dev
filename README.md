# teste-para-dev 

Zallpy e Agibank 


## Considerações do desenvolvedor

Este projeto usa a biblioteca do apache camel para criar um observador que monitora a pasta %HOMEPATH%/data/in.

Sempre que novos arquivos com extenção .dat são adicionados, o observer processa todos os arquivos para extrair informações de vendas.

* A classe responsável por inicializar o apache camel é a FileRoute.java;
* A classe reponsável por processar o arquivo da pasta **in** é a FileProcessor.java;
* No pacote **com.zallpy.fileprocessor.dto** tem todos as classes de negócio utilizadas no processamento das informações;


## Instruções de deploy ##

Por se tratar de uma aplicação feita com spring-boot e maven, use o seguinte comando:

``` mvn package spring-boot:run ```

Após feito o deploy, na pasta /home/usuario/data/in ou C:\users\usuario\data\in, basta adicionar os arquivos a serem processados, imediatamente serão processados e movidos para ./done, o arquivo de saída com o relatório final entra-se na pasta /home/usuario/data/out e foi chamado de REPORT_FINAL.done.dat.

## para verificar a cobertura de testes ##

A aplicação utiliza o plugin Jacoco, este é utilizado para realizar metricas de cobertura de testes. 

Para executar a verificação utilize o seguinte comando:

``` mvn verify ```

Será criado um arquivo HTML contendo o relatório dos testes, ao final da execução, na pasta ./target/site/jacoco/index.html

No arquivo pom.xml é possível definir uma serie de configurações ao jacoco, por exemplo o nível aceitável de linhas cobertas. Para a aplicação foi definido 80% de cobertura como aceitável.


#### Formato de Saída ####

O arquivo gerado foi chamado de REPORT_FINAL.done.dat, e segue o seguinte padrão:

``` 
Total Clients ç Total Salesman ç Best Sale Id ç Best Sale total ç Worst Sale Id ç Worst Sale Total ç Best Salesman Name ç Worst Salesman Name
62ç62ç91ç2.000ç90ç10çMARIAçJOAO
```

A primeira linha são os cabeçalhos do arquivo e a segunda linha são os valores referentes a cada coluna.

Obs.: Após processar um dado arquivo o mesmo é retirado da pasta **in** e movido para **in/done**, para evitar processamento duplicado.

O caminho das pastas de entrada e saída são obtidos a partir da variavél de ambiente por meio da seguinte linha de código:

``` System.getProperty("user.home") ```

* Windows: C:\\Users\\algum_usuario
* Linux: /home/usuario



## Descrição da Atividade ##


### Teste para Desenvolvedor ###

Você deve criar um sistema de análise de dados, onde o sistema deve importar
lotes de arquivos, ler e analisar os dados e produzir um relatório.

Existem 3 tipos de dados dentro desses arquivos.

Para cada tipo de dados há um layout diferente.

### Dados do vendedor ###

Os dados do vendedor têm o formato id 001 e a linha terá o seguinte formato.

``` 001çCPFçNameçSalary ```

### Dados do cliente ###

Os dados do cliente têm o formato id 002 e a linha terá o seguinte formato.

``` 002çCNPJçNameçBusiness Area ```

### Dados de vendas ###

Os dados de vendas têm o formato id 003. Dentro da linha de vendas, existe a lista
de itens, que é envolto por colchetes []. A linha terá o seguinte formato.

``` 003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name ```

### Dados de Exemplo ###

O seguinte é um exemplo dos dados que o sistema deve ser capaz de ler.

```
001ç1234567891234çPedroç50000
001ç3245678865434çPauloç40000.99
002ç2345675434544345çJose da SilvaçRural
002ç2345675433444345çEduardo PereiraçRural
003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro
003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo
```

### Análise de dados ###

Seu sistema deve ler dados do diretório padrão, localizado em 

``` %HOMEPATH%/data/in ``` .

O sistema deve ler somente arquivos .dat.

Depois de processar todos os arquivos dentro do diretório padrão de entrada, o
sistema deve criar um arquivo dentro do diretório de saída padrão, localizado em

``` %HOMEPATH%/data/out ``` .

O nome do arquivo deve seguir o padrão, {flat_file_name}.done.dat.

O conteúdo do arquivo de saída deve resumir os seguintes dados:

* Quantidade de clientes no arquivo de entrada
* Quantidade de vendedor no arquivo de entrada
* ID da venda mais cara
* O pior vendedor

O sistema deve estar funcionando o tempo todo.

Todos os arquivos novos estar disponível, tudo deve ser executado
Seu código deve ser escrito em Java.

Você tem total liberdade para utilizar google com o que você precisa.

Sinta-se à vontade para escolher qualquer biblioteca externa se for necessário.
 
### Critérios de Avaliação ###

* Clean Code
* Simplicity
* Logic
* SOC (Separation of Concerns)
* Flexibility/Extensibility
* Scalability/Performance


