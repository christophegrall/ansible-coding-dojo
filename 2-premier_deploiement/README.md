# Instructions TP2

## L'objectif est de réaliser un premier déploiement avec Ansible.

1- Editer playbook.yml pour compléter les tâches en utilisant les modules Ansible officiels.

http://docs.ansible.com/ansible/latest/modules_by_category.html

Nous déploierons notre service demo sur 'slave1'.

    ansible-playbook -i inventory playbook.yml

Une fois l'application déployée on peut vérifier qu'elle tourne.

    curl http://192.168.61.11:8080/

nous renvoie un message "Welcome from default-server"

2- Ajouter l'idempotence à la tâche "(re)Start myapp.service"

Pour ce faire on va capturer la sortie de la tâche "Copy executable jar" dans une variable.

Si cette tâche a effectué des changements alors on redémarre le service.