Instructions TP3
===

Cette fois nous allons introduire l'utilisation des variables.
On repart de l'exercice précédent.
1- On renseigne les variables suivantes dans `/host/vars/slave1.yml` (à compléter)

    app:
    base_dir:
    base_app_dir:
    base_log_dir:
    app_dir:
    conf_dir:
    log_dir:
    deploy_user:
    deploy_group:
    app_jar:
    java_opts:

2- Editer playbook.yml pour compléter les tâches en utilisant les modules Ansible officiels.

http://docs.ansible.com/ansible/latest/modules_by_category.html

3- A la ligne "Wait for the HTTP port to be listening" mettre une valeur par défaut de 60 secondes pour attendre que le port 8080 soit en écoute et vérifier qu'il s'agit bien d'un integer

4- Déployer un fichier application.properties externe en ajoutant le paramètre de JVM suivant au démarrage de l'application.

    -Dspring.config.location={{conf_dir}}/application.properties

définir les 2 valeurs suivantes dans `application.properties`

    server_name=
    logging.file=

NB : Pour surcharger server_name on peut utiliser des facts

3- Déployer le service demo sur 'slave1' en lui passant le paramètre `actuator_enabled=True`.

    ansible-playbook -i inventory playbook.yml -e "actuator_enabled=True"

Utiliser ce paramètre pour piloter la ligne de commande utilisée au démarrage de l'application

    --endpoints.enabled=<true/false>

Vérifier que le paramètre a bien l'effet attendu.

    curl http://192.168.61.11:8080/health
    {"status":"UP"}
