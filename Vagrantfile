# -*- mode: ruby -*-
# vi: set ft=ruby :
# AnsibleCoding dojo
VAGRANTFILE_API_VERSION = "2"
Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|


	config.vm.box = "bento/ubuntu-17.04"
#  config.vm.box = "hashicorp/precise32"

	config.vm.provider "virtualbox" do |v|
		v.memory = 1024
		v.cpus = 1
		v.customize ["modifyvm", :id, "--cableconnected1", "on"]
	end

	config.vm.define "master" do |master|
		master.vm.network :private_network, ip: "192.168.61.10"
		master.vm.network "forwarded_port", guest: 80, host: 8010
	end

	config.vm.define "slave1" do |slave1|
		slave1.vm.network :private_network, ip: "192.168.61.11"
		slave1.vm.network "forwarded_port", guest: 80, host: 8011
	end

	config.vm.define "slave2" do |slave2|
		slave2.vm.network :private_network, ip: "192.168.61.12"
		slave2.vm.network "forwarded_port", guest: 80, host: 8012
	end

end
