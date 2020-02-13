# Générateur d'UIDs pour des documents d'identités

## Caractéristiques de l'identifiant
- l'identifiant est unique. Celui-ci a une longueur de 9 caractères et est constitué uniquement de chiffres.
- l'identifiant n'est pas prédictible
- l'identifiant se base sur un générateur de nombres pseudo aléatoires cryptographiquement fort

## Prérequis
- Disposer d'une base de donnée PostgresSQL en écoute sur le port 5432
- Java 

## Installation
Pour configurer ce projet, clonez d'abord le référentiel suivant :
```bash
$ git clone https://github.com/laportemario/uid.git
```
et placez vous dans le répertoire uid
```bash
$ cd uid
```
## Lancement du serveur
Placez vous dans le répertoire server
```bash
$ cd server
```
Utilisez le script mvnw pour installer le projet.
```bash
$ ./mvnw install
```

Pour executer le programme, placez vous dans le répertoire target qui vient d'être généré et utilisé la commande suivante :
```bash
$ java -jar uid-0.0.1.jar
```

## Utilisation
Pour effectuer des requêtes REST à l'API vous devez disposer d'un token JTW.
Demande du jeton : 
```bash
$ TOKEN=$(curl -s -X POST -H 'Accept: application/json' -H 'Content-Type: application/json' --data '{"username":"mario","password":"metroscope"}' localhost:8080/authenticate | sed -n 's|.*"token":"\([^"]*\)".*|\1|p')
```

2 services sont exposés au endpoint "/uid" :
- Une requête POST permettra de générer un nouvel UID à la volé
- Une requête GET qui récupérera un UID déjà présent en base de donnée et qui n'a pas déjà été attribué

```bash
$ curl -s -X POST -H 'Accept: application/json' -H "Authorization: Bearer $TOKEN" http://localhost:8080/uid
```
```bash
$ curl -s -X GET -H 'Accept: application/json' -H "Authorization: Bearer $TOKEN" http://localhost:8080/uid
```
Exemple de réponse : 
```json
{
 "id":"089599550"
}
```


## Lancement du client
Placez vous dans le répertoire client
```bash
$ cd client
```

Installer les modules npm
```bash
$ npm install
```

Démarrer l'application
```bash
$ npm start
```
L'application est disponible à l'adresse http://localhost:3030 depuis votre navigateur.

