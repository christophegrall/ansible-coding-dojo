
# TP5 Base de données - loadbalancing

## Objectif

* Installer et configurer  une instance postgresql sur le host master
* Installer et configurer Apache server sur le host master
* Déployer l'application __service-personne__  sur les hosts slave1 et slave2

## Prérequis
Installer le rôle **geerlingguy.postgresql** depuis ansible-galaxy
```
ansible-galaxy install -r requirements.yml
```

##  Installer et configurer postgresql
1. Ajouter le rôle **geerlingguy.postgresql** au playbook
2. En utilisant des variables de groupe pour masters

Utiliser la documentation du plugin postgresql pour 

1. ajouter les entrées hba suivantes:


```
    local all postgres peer
    type all all 0.0.0.0/0 trust
```

2. configurer les options globales pour écouter sur toutes les adresses

```
listen_addresses *
```

3. créer la database nommée `coding_dojo`

4. créer le user : `postgresql/postgresql`

5. Ajouter la **post_task** au playbook

```
post_tasks:

- postgresql_schema:
    name: personne
    database: coding_dojo
    login_user: postgres
    login_password: postgres
    login_host: 192.168.61.10
    owner: postgres
    state: present
```

## Installer et configurer Apache server en reverse proxy

1. Créer le rôle apache2-reverse-proxy
2. Compléter les tasks pré-définis
3. Dans le répertoire  **/templates**, compléter **src.j2**

```
ProxyPreserveHost On
ProxyRequests On

<Proxy balancer://mycluster>
  ...
</Proxy>

ProxyPass / balancer://mycluster/
ProxyPassReverse / balancer://mycluster/
```

## Déployer l'application __service-personne__
