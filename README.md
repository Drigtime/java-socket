# IUT Java Socket

## Informations
* Il est possible de fermer le client en tapant la commande `/exit` dans le champs de texte
* Il est possible d'exécuter plusieurs instances de client

## Prérequis
* [Docker](https://www.docker.com/)
* [Docker compose](https://docs.docker.com/compose/install/)
* [Java](https://www.java.com/fr/download/)

## Guide d'utilisation

### Création de la base de donnée
1. Ouvrir un Terminal à la racine du projet 
2. Exécuter la commande `docker-compose up` pour initialiser un environnement contenant une base de donnée

### Exécuter le programme
> Si vous voulez utiliser le programme sans le compiler, sachez qu'il vous faudra la version 15 de Java minimum
1. Ouvrir un Terminal à la racine du projet 
2. Exécuter le serveur qui vas écouter sur le port **5000** les messages en provenance du client `java -jar out/artifacts/server_jar/iut-projet-final.jar`
3. Ouvrir le fichier étant à l'emplacement `out/artifacts/client_jar/iut-projet-final.jar` pour accéder à l'interface client
4. Dans l'interface client vous pouvez envoyer des messages qui seront envoyés au serveur et ensuite sauvegardés en base de données localement

### Compiler le code soi-même
> Dans cette partie nous allons détailler la procédure à suivre en utilisant IntelliJ
1. Ouvrir le projet dans IntelliJ
2. Dans la barre des menus, selectionnez `Construire > Construire un artefact`
3. Suivre les étapes pour [exécuter le programme](#exécuter-le-programme)

Quand vous avez fini vous pouvez supprimer des containers docker avec la commande `docker-compose down`
