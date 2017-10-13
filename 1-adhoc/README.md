Instructions TP1
===

Installer Ansible
Vérifier qu’il fonctionne : ansible --version

Installer vagrant
Vérifier qu’il fonctionne: vagrant --version

Se placer dans le projet coding-dojo-ansible
Démarrer les 3 vms avec vagrant up
Ajouter votre clé SSH aux 3 vms

En s’appuyant sur le VagrantFile, renseigner le fichier inventory au format ansible (ini).
Déclarer un unique groupe de machines nommé cluster.

Vérifier que tout fonctionne (au choix): 
ansible -i inventory cluster -m ping
ansible -i inventory all -m ping
ansible -i inventory cluster -m shell -a 'ls -la'
ansible -i inventory cluster -a "/bin/date"
