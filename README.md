Projet Programmation Mobile


{nomAppli} est une application de finance déstinée à l'utilisation mobile pour avoir des informations sur les entreprises côtées en bourse.

La conception de l'application a débuté avec les Travaux Dirigés du cours de Programmation Mobile. L'application a été réalisée pendant les mois de Mars et Avril.

{logo application}
{aperçu avec capture d'écran}

Langage utilisé : Java

Environnement de développement : Android Studio

Outil de compilation : Gradle

Notions de programmation abordées :

      - Programmation Orientée Objet

      - Design
      
      - Recyclerview
      
      - Appel Webservice à une API Rest
      
      - Stockage des données en cache
      
      - Notifications Push
      
      - Git flow
       
Autres ressources : Github, FireBase, {autres}

Lien vers API Documentation : https://financialmodelingprep.com/developer/docs/ (Financial Modeling Prep)

L'application affiche une liste de type Recyclerview qui permet à l'utilisateur de voir à l'écran la totalité des entreprises côtées avec leur nom et leur Ticker (symbole en finance pour l'identification). 
En appuyant sur une des entreprises, l'utilisateur accède à un autre écran qui lui permet de voir les détails de cette dernière ({prix, bourse, etc}).
Lorsque l'application est fermée, l'utilisteur reçoit quotidiennement une notification Push grâce au Cloud Messaging FireBase. Le message l'invite à acheter des actions :D .
