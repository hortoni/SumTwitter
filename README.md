# SumTwitter
SumOne Programming Challenge - Mobile Developer

O aplicativo foi desenvolvido para a plataforma Android, com versão mínima 2.3, no Android Studio 1.3 e foi utilizado o Gradle na versão 1.2.3 para automatização da build do projeto.

O aplicativo contém 3 telas:
- Login
- Main
- Profile

# Arquitetura
A integração com o Twitter foi feita através da API <a href="https://get.fabric.io/">Fabric</a>, mantida pela própria equipe do Twitter. Ela é responsável por abstrair as chamadas aos serviços REST que o Twitter fornece, facilitando o desenvolvimento do aplicativo.


# Aplicativo
A primeira tela do aplicativo é a tela de login. Com um botão de login, essa tela é responsável por autenticar o usuário em sua conta pessoal do Twitter e implementar o Single Sing On (SSO).

Ao conectar em sua conta, o usuário poderá observar 2 fragmentos na tela. Um contendo sua timeline com os últimos 50 tweets e o outro com um mapa com os tweets geolocalizáveis. No fragmento da timeline é possível recarregar os tweets com o gesto de swipe de cima para baixo.

Ao clicar em algum tweet em sua timeline ou em seu mapa, a tela de profile será chamada e mostrará os tweets do usuário correspondente.


