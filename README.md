# Marvel Universe

O Marvel Universe é um applicativo que lista os heróis da Marvel, todas as informações do app foram obtidas através da API da Marvel (https://developer.marvel.com/).

A arquitura utilizada foi o MVVM com injeção de dependência com o Koin.

![Screenshot_1675689374](https://user-images.githubusercontent.com/29706435/216981408-59ddb6d8-4e93-4932-b032-3983caa86557.png)


## Bibliotecas utilizadas

**Retrofit v2.9.0**

Utilizada para requisições HTTP.

**Okhttp v4.10.0**

Utilizada em conjunto com o Retrofit para adicionar interceptadores HTTP.

**Glide v4.14.2**

Para carregamento de imagens.

**Coroutines v1.6.1**

Padrão de projeto para facilitar e simplificar código de execução assíncrona.

**Lifecycle ViewModel v2.5.1**

Utilização da arquitetura MVVM.

**Koin v3.3.2**

Injeção de dependência

**Mockk v1.13.4 **

Mocks para testes unitários.

**Flow Turbine**

Ferramenta para facilitar os testes unitários com Kotlin Flow.


## Roadmap

Adição de banco de dados local para o funcionamento offline.

Adição de uma página de detalhes do herói.

Busca de heróis.

Possibilidade de adicionar um herói como favorito.

Listagem dos heróis favoritos.
