#!/bin/bash

#todo generate key and cleaning

echo Copying the key to the remote machine

SERVER_LIST=./servers.txt

while read REMOTE_SERVER
do
  echo $REMOTE_SERVER
  ssh-copy-id -f -i $HOME/.ssh/id_rsa.pub -o 'StrictHostKeyChecking=no' vagrant@$REMOTE_SERVER
done < $SERVER_LIST
