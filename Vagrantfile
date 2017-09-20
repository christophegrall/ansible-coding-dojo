# -*- mode: ruby -*-
# vi: set ft=ruby :
# AnsibleCoding dojo
VAGRANTFILE_API_VERSION = "2"
Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|

	config.vm.box = "ansible-ubuntu-1204-i386"
	config.vm.box_url = "https://cloud-images.ubuntu.com/vagrant/precise/current/precise-server-cloudimg-i386-vagrant-disk1.box"

	config.vm.define "master" do |lb|
		lb.vm.network :private_network, ip: "192.168.61.10"
	end

	config.vm.define "slave1" do |db|
		db.vm.network :private_network, ip: "192.168.61.11"
	end

	config.vm.define "slave2" do |db|
		db.vm.network :private_network, ip: "192.168.61.12"
	end

end
