# Il me faut de la place  - 1ère partie
**Vivien Louradour - IMT Atlantique FIL A1**  
*Date de rendu : 22.11.2017*

Version minimum java : 8



## Utilisation de l'API
La seule classe à utiliser pour développer l'IHM sera `Core.Api`.  
Celle-ci est instanciée avec un chemin vers un répertoire (ou fichier), et créer à ce moment-là un arbre représentant l'arborescence ayant comme racine le répertoire(ou fichier) passé en paramètre.  
A partir de cette arborescence, il est possible d'appeler plusieurs méthodes renvoyant des `TreeModel`.  
Voir exemple d'utilisation dans la classe `Test.TestIhm`.  

## Représentation objet de l'arborescence de fichiers
#### CustomTree
Un arbre est créé lors de la création d'une instance de la classe `Core.Api`, appelé `customTree` dans le code.  
L'arbre est composé des classes `Core.FileNode` et `Core.DirectoryNode` qui héritent de la classe abstraite `Core.Node`.  
La classe `Core.CustomTreeFactory` s'occupe de la construction de cet arbre.
#### TreeModel
Le CustomTree construit est ensuite transformé en `javax.swing.tree.DefaultTreeModel`, un arbre qui servira à l'affichage de l'arborescence dans l'IHM swing.  
Chaque noeud du treeModel porte une instance de `Core.FileNode` ou `Core.DirectoryNode`.  
Le treeModel peut être construit tel quel (même arborescence que le customTree) ou en appliquant des filtres implémentant l'interface `java.io.FileFilter`.

## Implémentation du cache
Afin d'éviter de hasher plusieurs fois des fichiers inchangés, un cache est utilisé pour mémoriser les hash.  
A chaque recherche de doublons, si le fichier n'est pas présent ou pas à jour dans le cache, le hash est (re)calculé puis mis à jour/inséré dans le cache.  
Sinon, le hash est juste récupéré dans le cache.  
Ce fichier s'appelle `cache_hash.ser` et est créé à la racine du projet.  
Une méthode permet de nettoyer le cash : tous les fichiers présents dans le cache mais pas dans l'arborescence de la machine seront supprimés du cache.



