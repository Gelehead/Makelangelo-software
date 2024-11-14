Modifications apportées:

Le premier problème qui nous a forcé à faire des modifications au fichier est le fait que chaque flag créait un fichier qui avait le même nom, ce qui faisait planter la GitHub action dès le deuxième flag.

Pour résoudre ce problème, nous avons ajouté un step qui permet de modifier le nom du fichier créé en fonction  du nom du flag en cours. Cela permet d'avoir un fichier différent par flag. Pour ce faire, nous ajoutons simplement le nom du flag comme variable d'environnement.

Ensuite, nous avons rencontré le problème que le nom des flags pouvait contenir des caractères illégaux. Donc nous avons du ajouter une commande qui retire ces caractères. Nous avons choisi la commande sed, car elle permet de simplement trouver et remplacer les caractère qui ne sont pas des lettres. Cela pourrait causer des problèmes si nous choisissions des flags qui n'ont comme différence de nom que des caractères qui ne sont pas des lettres, mais ce n'est pas le cas des flags que nous avons choisi.

Flags et leurs justifications : 

