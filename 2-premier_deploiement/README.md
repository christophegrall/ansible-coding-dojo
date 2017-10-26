# Instructions TP2

## L'objectif est de réaliser un premier déploiement avec Ansible.

1- Editer playbook.yml pour compléter les tâches en utilisant les modules Ansible officiels.

http://docs.ansible.com/ansible/latest/modules_by_category.html

La structure du déploiement est la suivante :

    /custom/
    |-- app
    |   |-- myapp
    |   |   `-- myapp.jar
    |   `-- myapp-conf
    |       `-- application.properties
    `-- log
        `-- myapp
            `-- myapp.log

Nous déploierons notre service demo sur 'slave1'.

    ansible-playbook -i inventory playbook.yml

Une fois l'application déployée on peut vérifier qu'elle tourne.

    curl http://192.168.61.11:8080/

nous renvoie un message "Welcome from default-server"
