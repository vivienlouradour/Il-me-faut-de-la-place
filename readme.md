# Il me faut de la place  - 1ère partie
**Vivien Louradour - IMT Atlantique FIL A1**  
*Date de rendu : 22.11.2017*  
*Code utilisé par Cédric Garcia pour la deuxième partie*

Version minimum java : 8

Documentation détaillée [disponibles ici](/doc/designBackground.md)  
Javadoc [disponibles ici](/doc/javadoc/index.html)

## Liste des fonctionnalités fournies par l'API : 
+ Créer une arborescence à partir d'une racine
+ Possibilité d'appliquer des filtres à cette arborescence
+ Recherche de doublons dans l'arborescence
  + Gestion d'un cache pour les doublons pour éviter de les recalculer à chaque fois
  + Possibilité de "nettoyer" le cache lorsqu'il devient trop lourd
+ Récupération du poids totale d'un fichier dans l'arbre

Les détails des choix d'implémentation sont [disponibles ici](/doc/designBackground.md)

## Lancement du ".jar"
Le fichier `Il-me-faut-de-la-place.jar` permet de tester les principales fonctionnalitées du projet en ligne de commande.  
On le lance grâce à la commande `java -jar il-me-faut-de-la-place.jar arguments`   
Liste des arguments : 
+ -h ou -help : affiche l'aide (annule tous les autres arguments)
+ -r <root_path> : chemin vers le repertoire à analyser (argument obligatoire)
+ -f <regex> : applique un filtre à l'arborescence qui valide l'expression régulière regex. (Non valable pour le calcul de doublons)
+ -d : affiche les doublons contenus dans l'arborescence de fichiers
+ -w : affiche le poids total de l'arborescence
+ -p : affiche la structure de l'arborescence
+ -t : affiche le temps d'éxecution




