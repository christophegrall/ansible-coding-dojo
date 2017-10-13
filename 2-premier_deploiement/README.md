Instructions TP2
===

L'objectif est de réaliser un premier déploiement avec Ansible.

Editer playbook.yml pour compléter les tâches en utilisant les modules Ansible officiels.

http://docs.ansible.com/ansible/latest/modules_by_category.html

    # After the playbook has been ran
    # curl http://192.168.61.11:8080/ should return a message "Welcome from default-server"

ansible-playbook -i inventory playbook.yml -e "actuator_enabled=True"

curl http://192.168.61.11:8080/health
{"status":"UP"}


