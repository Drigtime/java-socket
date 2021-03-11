# IUT Java Socket

## Informations
* Il est possible de fermer le client en tapant la commande `/exit` dans le champs de text
* Il est possible d'exécuter plusieurs instance de client

## Prérequis
* [Docker](https://www.docker.com/)
* [Docker compose](https://docs.docker.com/compose/install/)
* [Java](https://www.java.com/fr/download/)

## Guide d'utilisation

### Création de la base de donnée
1. Ouvrir un Terminal à la racine du projet 
2. éxécuter la commande `docker-compose up` pour initialiser un environnement contenant une base de donnée

### Exécuter le programme
> Si vous voulez utiliser le programme sans le compiler, sachez qu'il vous faudra la version 15 de Java minimum
1. Ouvrir un Terminal à la racine du projet 
2. éxécuter le server qui vas écouter sur le port **5000** les messages en provenance du client `java -jar out/artifacts/server_jar/iut-projet-final.jar`
3. Ouvrir le fichier étant a l'emplacement `out/artifacts/client_jar/iut-projet-final.jar` pour accéder a l'interface client
4. Dans l'interface client vous pouvez envoyer des messages qui seront envoyer au serveur et ensuite sauvegardé en base de donnée localement

### Compiler le code soit même
> Dans cette partie nous allons détailler la procédure a suivre en utilisant IntelliJ
1. Ouvrir le projet dans IntelliJ
2. Dans la barre des menu selectionnez `Construire > Construire un artefact`
3. Suivre les étapes pour [exécuter le programme](#exécuter-le-programme)

Quand vous avez fini vous pouvez supprimer des container docker avec la commande `docker-compose down`
