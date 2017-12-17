# Il me faut de la place  - 1ère partie
**Vivien Louradour - IMT Atlantique FIL A1**  
*Date de rendu : 22.11.2017*  
*`Code utilisé par Cédric Garcia pour la deuxième partie`*

Version minimum java : 8

Documentation détaillée [disponibles ici](/doc/designBackground.md)  
Javadoc [disponibles ici](/doc/javadoc)

## Contexte
Ce projet fait parti du cours "Actualisation des Compétences en Développement et Conception" de première année en filière Ingénierie Locigielle de l'Institut Mines-Télécom.  
Il s'agit de développer une application multi-plateforme, capable de scanner une arborescence de fichier, afin de libérer de l'espace.  
Le projet est structuré en deux parties : chacun développe la première partie, puis un échange de code est effectué et nous développons la deuxième partie à partir du code d'un binôme. 
#### Partie 1 : backend
Il s'agit de fournir l'ensemble des fonctionnalités nécessaire au projet, sous forme d'une API.
#### Partie 2 : IHM  
La deuxième partie consiste à développer une interface graphique s'appuyant sur l'API développé dans la 1ère partie.  
  
Ce repository git contient le code et le descriptif de la première partie du projet.

## Liste des fonctionnalités fournies par l'API : 
+ Créer une arborescence à partir d'une racine
+ Possibilité d'appliquer des filtres à cette arborescence
+ Recherche de doublons dans l'arborescence
  + Gestion d'un cache pour les doublons pour éviter de les recalculer à chaque fois
  + Possibilité de "nettoyer" le cache lorsqu'il devient trop lourd
+ Récupération du poids totale d'un fichier dans l'arbre
+ Stockage des erreurs pour un éventuel affichage dans l'IHM 

Les détails des choix d'implémentation sont [disponibles ici](/doc/designBackground.md)

## Lancement du ".jar"
Le fichier `Il-me-faut-de-la-place.jar` permet de tester les principales fonctionnalitées du projet en ligne de commande.  
On le lance grâce à la commande `java -jar il-me-faut-de-la-place.jar arguments`   
##### Liste des arguments :
+ -h ou -help : affiche l'aide (annule tous les autres arguments)
+ -r <root_path> : chemin vers le repertoire à analyser (argument obligatoire)
+ -f <regex> : applique un filtre à l'arborescence qui valide l'expression régulière regex. (Non valable pour le calcul de doublons)
+ -d : affiche les doublons contenus dans l'arborescence de fichiers
+ -w : affiche le poids total de l'arborescence
+ -p : affiche la structure de l'arborescence
+ -t : affiche le temps d'éxecution
+ -e : affiche les erreurs qui se sont produites au cours de l'exécution

## Utilisation de l'API pour l'IHM
La seule classe à utiliser pour développer l'IHM sera `Core.Api`.  
Celle-ci est instanciée avec un chemin vers un répertoire (ou fichier), et créer à ce moment-là un arbre représentant l'arborescence ayant comme racine le répertoire(ou fichier) passé en paramètre.  
A partir de cette arborescence, il est possible d'appeler plusieurs méthodes renvoyant des `TreeModel`.  
Voir exemple d'utilisation dans les classes `Test.TestIhm` et `Test.TestCommandLine`.  




