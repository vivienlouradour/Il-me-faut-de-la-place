#Il me faut de la place  - 1ère partie
*Vivien Louradour - IMT Atlantique FIL A1*  
_Date de rendu : 22.11.2017_

##Utilisation de l'API
La seule classe à utiliser pour développer l'IHM sera `Core.Api`  
Celle-ci est instanciée avec un chemin vers un répertoire (ou fichier), et créer à ce moment-là un arbre représentant l'arborescence ayant comme racine le répertoire(ou fichier) passé en paramètre.  
A partir de cette arborescence, il est possible d'appeler plusieurs méthodes renvoyant des `TreeModel`  
Voir exemple d'utilisation dans la classe `IHM.Test.Test`  

##Implémentation du cache
Afin d'éviter de hasher plusieurs fois des fichiers inchangé, un cache est utilisé pour mémoriser les hash.  
A chaque recherche de doublons, si le fichier est n'est pas présent ou pas à jour dans le cache, le hash est calculé puis inséré dans le cache.  
Sinon, le hash est juste récupéré dans le cache.  
Ce fichier s'appelle `cache_hash.ser` et est créé à la racine du projet.  



