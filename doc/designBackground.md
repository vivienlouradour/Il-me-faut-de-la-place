# Il me faut de la place - 1ère Partie
Ce fichier détaillera les choix d'implémentations effectués, et l'architecture du code.
## 1. Les contraintes d'implémentation  
Afin de faciliter l'échange de code, et l'implémentation d'une IHM sur le code d'un binôme, nous nous sommes mis d'accord sur les principaux services fournis par l'API.  
+ L'API doit être capable de fournir, à partir d'un chemin absolu vers un répertoire, un `TreeModel` représentant l'arborescence de fichiers du répertoire.  
Le `TreeModel` sera ensuite utilisé en `swing`dans l'IHM afin d'afficher l'arborescence.
+ Il doit être possible d'appliquer des filtres sur cette arborescence (par exemple récupérer seulement les fichiers ayant une certaine extension).
+ L'API doit repérer les doublons dans l'arborescence. On appel doublon des fichiers ayant un contenu identique, même si le nom diffère.   
## 2. Les choix d'implémentation 
Voilà la classe `Core.Api`, qui fournit les méthodes utilisables dans l'IHM : 
![alt text](img/Api.png)  
### 2.1. L'arborescence de fichiers
Après plusieurs test de structure d'arborescence, j'ai décidé d'utiliser deux structures distinctes.
#### 2.1.1 CustomTree
A partir d'un chemin absolue, l'API construit un `CustomTree`.  
Il s'agit d'un arbre implémenté à l'aide du patron composite : il est composé des classes `Core.FileNode` et `Core.DirectoryNode` qui héritent de la classe abstraite `Core.Node`.
![alt text](img/CustomTree.png)  
Cette structure est immutable, et elle servira de référence pour construire les differents `TreeModel`.    
La classe `Core.CustomTreeFactory` s'occupe de la construction récursive de cet arbre.  
J'ai décidé que chaque noeud porte l'instance de `java.io.File` représentant le fichier, et que cette instance soit accessible depuis l'IHM, cela évite de redéfinir innutilement les méthodes déjà implémentées dans la classe `File` (comme la suppression, la modification d'un fichier, etc.).  
La seule méthode que j'ai jugé nécessaire de réimplémentée est la méthode renvoyant la taille d'un fichier : celle de la classe `java.io.File` ne renvoit pas la taille totale dans le cas d'un répertoire. Cette taille est donc calculé pendant la création récursive de l'arbre, et accessible via la méthode `totalLength() : long`. 
#### 2.1.2 TreeModel
Le CustomTree construit est ensuite transformé en `javax.swing.tree.DefaultTreeModel`, un arbre qui servira à l'affichage de l'arborescence dans l'IHM swing.  
Chaque noeud du treeModel porte une instance de `Core.FileNode` ou `Core.DirectoryNode`, ce qui permet de récupérer les informations utiles sur le noeud (taille du fichier/répertoire, méthode de suppression/modification, etc.).  
Le treeModel peut être construit tel quel (même arborescence que le customTree) ou en appliquant des filtres implémentant l'interface `java.io.FileFilter`, sans pour autant reparcourir physiquement le disque dur (l'arborescence est déjà en mémoire via le `CustomTree`).
Cet arbre est construit grâce à la classe `Core.ModelTreeFactory`.
![alt text](img/ModelTree.png)  
### 2.2. L'application de filtres
Pour la gestion des filtres appliqués à l'arborescence, j'ai décidé d'implémenté seulement la méthode de validation des filtres.  
La méthode `getModelTree(FileFilter)` de la classe `Core.Api`, prend en paramètre un `FileFilter` qui est une classe abstraite.  
Le binôme qui développera l'IHM sera donc en mesure d'implémenté cette classe comme bon lui semble. Il me paraissait plus judicieux de ne pas le limiter dans ces choix de filtres.
### 2.3. La recherche de doublons 
Une des fonctionnalités principales de l'API est la recherche de doublons.
#### 2.3.1 Comment détécter les doublons ?
Nous avons réfléchie à plusieurs façon de détecter des doublons. Le seul moyen de réperer les doublons de façon sûr est de comparer les hashs des différents fichiers.  

#### 2.3.2 Comment optimiser la recherche des doublons ?
Cache 
### 2.4. Gestion des erreurs
## 3. Conclusion et améliorations



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

