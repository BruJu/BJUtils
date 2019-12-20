# BJUtils

Ce dépôt contient différentes classes et fonctions utilitaires que j'ai
développé pour mes projets personnels en Java.


## Reconnaissance de texte via des motifs

Le principe est le suivant :
* On ouvre l'image et on extrait une matrice avec vrai à l'endroit où on estime qu'il y a du texte et faux ailleurs. (cela correspond à si un pixel est allumé ou non, ie si son code couleur répond à une certaine fonction)
* On identifie les motifs qui composent l'image. On dit qu'il y a un motif quand on a une suite de colonnes dans la matrice avec au moins un vrai. On suppose donc que les lettres sont séparées par une colonne vide. (Comme ce n'est pas toujours vrai, on permet qu'un motif corresponde à une suite de lettres)
* On estime qu'il y a un espace si deux motifs sont séparés de 4 colonnes vides.
* Si le motif n'est pas dans la base de motif, on invite l'utilisateur a ajouté le motif reconnu dans la base de motifs. Sa seule tâche sera de remplacer le ? de la ligne par ce qu'il voit.
* Une fois tous les motifs de l'image vus, le programme restitue une chaîne qui est l'enchaînement des lettres représentées par le motif.

Si certains motifs ne sont pas reconnus, un message est ajouté dans la console.

### Exemple

Si le texte ne lui est pas connu, il essaye de reconnaître les lettres décrites sur l'image via une table de correspondance entre motifs et lettre (ou suite de lettre) qu'il possède.

Si un motif n'est pas reconnu, un affichage est fait pour signaler que le motif n'existe pas. Le même motif n'est pas signalé deux fois.

Un exemple d'exécution dans un autre programme visant à reconnaitre le texte
écrit sur une image en vue de l'utiliser pour associer le nom des ennemis avec
leurs statistiques :

~~~~
                                                                                                                            
                                                                                                                            
                                                                                                                            
                                                                                                                            
                                                                                                                            
                                                                                                                            
                                                                                                                            
                                              X                                                                             
    XX                                                                                                                      
    XX                                                                                                                      
   X  X    XXXX   XXXX   XXXX    XXXX   XXXX  X  X XXX                                                                      
   X  X   X      X           X  X      X      X  XX   X                                                                     
  X    X  X      X           X  X      X      X  X    X                                                                     
  X    X   XXX    XXX    XXXXX   XXX    XXX   X  X    X                                                                     
  XXXXXX      X      X  X    X      X      X  X  X    X                                                                     
 X      X     X      X  X    X      X      X  X  X    X                                                                     
 X      X XXXX   XXXX    XXXXX  XXXX   XXXX   X  X    X                                                                     
                                                
                                                
                                                                                                                                                          
Motif non reconnu :
   xx
   xx
  x  x
  x  x
 x    x
 x    x
 xxxxxx
x      x
x      x
? 24 24 36 36 66 66 126 129 129
Motif non reconnu :
 xxxx
x
x
 xxx
    x
    x
xxxx
? 30 1 1 14 16 16 15
Motif non reconnu :
 xxxx
     x
     x
 xxxxx
x    x
x    x
 xxxxx
? 30 32 32 62 33 33 62
Motif non reconnu :
x


x
x
x
x
x
x
x
? 1 0 0 1 1 1 1 1 1 1
Motif non reconnu :
x xxx
xx   x
x    x
x    x
x    x
x    x
x    x
? 29 35 33 33 33 33 33
== Identification == 
????????

~~~~

Si on ajoute la ligne "s 30 1 1 14 16 16 15" à la liste des motifs connus
(un constructeur de la classe Motif utilise ce genre de sérialisation que
l'on peut stocker dans un fichier)


~~~~
                                                  
                                                                                                                            
                                              X                                                                             
    XX                                                                                                                      
    XX                                                                                                                      
   X  X    XXXX   XXXX   XXXX    XXXX   XXXX  X  X XXX                                                                      
   X  X   X      X           X  X      X      X  XX   X                                                                     
  X    X  X      X           X  X      X      X  X    X                                                                     
  X    X   XXX    XXX    XXXXX   XXX    XXX   X  X    X                                                                     
  XXXXXX      X      X  X    X      X      X  X  X    X                                                                     
 X      X     X      X  X    X      X      X  X  X    X                                                                     
 X      X XXXX   XXXX    XXXXX  XXXX   XXXX   X  X    X                                                                     
                                                
                                                
                                                                                                                                                   
Motif non reconnu :
   xx
   xx
  x  x
  x  x
 x    x
 x    x
 xxxxxx
x      x
x      x
? 24 24 36 36 66 66 126 129 129
Motif non reconnu :
 xxxx
     x
     x
 xxxxx
x    x
x    x
 xxxxx
? 30 32 32 62 33 33 62
Motif non reconnu :
x


x
x
x
x
x
x
x
? 1 0 0 1 1 1 1 1 1 1
Motif non reconnu :
x xxx
xx   x
x    x
x    x
x    x
x    x
x    x
? 29 35 33 33 33 33 33
== Identification == 
?ss?ss??
~~~~

Une fois tous les motifs ajoutés au fichier motifsconnus en remplaçant les ? par le symbole correspondant on obtient.

~~~~
== Identification == 
Assassin
~~~~



## Licence

Creative Commons Zero
