# FireUpdaterLib

**Infos**

FireUpdaterLib est une librairie java permettant de télécharger des fichiers sur le web !
Cette lib est basée sur le travail de Shawiizz via sa lib [ShaLibUpdate](https://github.com/Shawiizz/ShaLibUpdate/). Je l'ai modifié à ma sauce d'ailleurs merci à lui !

*Important* !

Cette librairie utilise d'autres libs pour fonctionner (elles sont incluses dans ma lib) :

- [WyLog](https://github.com/wytrem/WyLog/) (pour les logs par Wytrem)
- [SLF4j](http://www.slf4j.org/) (pour les logs dépendance de WyLog)

**Features**

- Compatible Apache (NGINX non testé)
- Verification des fichiers avec l'algorithme SHA-1
- Fichier ignore.cfg sur le webserver qui permet de ne pas supprimer des fichiers en local lors du lancement du telechargement
- Simple d'utilisation
- Classe (GuiUpdater) qui permet de recup des infos sur le téléchargement (Fichiers téléchargés, Fichiers à télécharger)
- Générateur de dossier de jeu minecraft facile d'utilisation

**Comment l'utiliser ?**

Simple ! Il suffit de télécharger le serveur web [ici](http://fireblaim.ga/files/WebServer%20FireUpdaterLib.zip) et de le mettre le contenu sur votre site et de télécharger ma lib [ici](https://github.com/FireBlaim/FireUpdaterLib/releases/latest) (Prenez le .jar) ! Importer le jar dans votre projet...

Maintenant mettez dans votre code :

```java
FireUpdaterLib.startUpdate("votresite", new File("chemin de dossier"));
```

Et la mise à jour devrait fonctionner !

Si vous souhaitez faire un launcher minecraft par exemple et générer un dossier pour les fichiers facilement mettez ceci :

```java
FireUpdaterLib.startUpdate("votresite", MinecraftDirGenerator.createDir("nom de dossier"));
```

Aussi simple que cela !

*Comment recup les infos de téléchargement ?*

Une classe permet de recuperer ces infos (GuiUpdater)

Si vous souhaitez recup le nombre de fichiers téléchargés et le nombre de fichiers à télécharger des simples getters permettent de faire cela :

```java
//Recup le nombre de fichiers téléchargés : int simple
GuiUpdater.getDownloadedFiles(); 

//Recup le nombre de fichiers à télécharger : int simple
GuiUpdater.getFilesToDownload(); 
```

De nouvelles mises a jour pourrait modifier cette classe je modifierai cette aide en fonction...
