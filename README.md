FightQuiz
===========

Développé par Félix Meley & César Monnier
Contacts : felix.meley.etu@univ-lille.fr , cesar.monnier.etu@univ-lille.fr


# Présentation de FightQuiz

Combattez des monstres avec des duels de connaissance pour sauver votre famille de l'infâme Beschooler!
Des captures d'écran illustrant le fonctionnement du logiciel sont proposées dans le répertoire shots. (pas encore pour l'alpha)

À l'état actuel, le jeu est entierement fonctionnel, mais manque du contenu (combat contre le boss)


# Fonctions actuellement implentées

- Chargement des questions et réponses à partir de fichiers CSV
- Ordre des réponses aléatoires
- Apparance du monstre changeante selon la matière de la question
- Score donné selon le temps mis à répondre
- Enregistrement des scores et d'un pseudo avec
- Compteur de vies et de score affichés à l'écran
- Histoire et cinématiques
- Impossibilité de tomber plusieurs fois sur la même question
- Deux environnements de combat + petite cinématique de transition entre les deux


# Fonctions non-implentées

- Combat contre le Boss
- Marqueur sur l'écran des scores démarquant ceux ayant battu le jeu de ceux ayant perdu


# Utilisation de FightQuiz

Afin d'utiliser le projet, il suffit de taper les commandes suivantes dans un terminal :

```
./compile.sh
```
Permet la compilation des fichiers présents dans 'src' et création des fichiers '.class' dans 'classes'

```
./run.sh
```
Permet le lancement du jeu

Pour un affichage correct, veuillez laisser la taille de votre terminal en 80x24 (généralement la taille par défaut).
Vous pouvez par contre zoomer (Affichage > Zoom avant) pour un affichage plus grand.

La fonction suivante permet la réinitialisation des scores enregistrés (attention, pas de retour possible!)

```
./resetScores.sh
```
