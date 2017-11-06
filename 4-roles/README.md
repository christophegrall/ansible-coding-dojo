
# TP4 Rôles

## Objectif

Découper  le playbook du TP précédent en plusieurs rôles ansible.

## Création de rôle
1. Créer le répertoire __roles__
2. Aller dans le répertoire roles
3. Initialiser la structure du rôle __standalone-webapp__
```
ansible-galaxy init standalone-webapp
```
4. Lister l'arborescence créée
```
tree -d
```  
5. Editer **standalone-webapp/tasks/main.yml** avec le contenu du playbook du TP 3.
6. Editer **playbook.yml** pour appeler le rôle standalone-webapp


### Décomposition en plusieurs rôles
1. Créer le rôle __java__ et ses tasks
2. Créer le rôle __prepare__ et ses tasks
3. Créer le rôle __app-conf__ et ses tasks
4. Créer le rôle __standalone-webapp__ et ses tasks
