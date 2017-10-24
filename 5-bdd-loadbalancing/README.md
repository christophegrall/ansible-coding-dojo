
# TP5 bdd -  loadbalancing

L'objectif du TP est déployer le service-personne sur les instances slave1 et slave2 du groupe  slaves.

## Démarrage

Le répertoire du TP est le suivant :

### Prérequis

Exécuter

```
ansible-galaxy install -r requirements.yml
```

### Exécution

Jouer votre playbook !

```
ansible-playbook -i inventory playbook.yml -v
```
