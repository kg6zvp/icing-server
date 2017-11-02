# Icing Node

## Install

### Prerequisites

#### Raspberry Pi

//Install CentOS using the instructions found [here](https://wiki.centos.org/SpecialInterestGroup/AltArch/Arm32/RaspberryPi3)

Install Fedora using the instructions found [here](https://fedoraproject.org/wiki/Architectures/ARM/Raspberry_Pi)

Follow the regular instructions for Fedora

#### Mac OS X

`//TODO: do we care?`

#### Fedora

1. `$ sudo dnf install java-1.8.0-openjdk-devel maven git`

#### CentOS

1. `$ sudo yum install java-1.8.0-openjdk-devel maven git`

#### Debian

1. `$ sudo apt-get install ???? git`

### Installation

2. `$ mkdir -p ~/git`

1. `$ cd ~/git`

1. `$ git clone 'http://gitlab.mccollum.enterprises/icing/media.git'`

1. `$ cd media`

## Run

### Standalone

Change to the directory (`$ cd ~/git/media`)

Run with:
`$ ./icing.sh`

(this will create database files named `icing-database.h2.db` and `icing-database.trace` to store your data)

### On App Server (like Wildfly)

Change to the directory (`$ cd ~/git/media`)

Create a war for deployment with:
`$ mvn clean -DskipTests package`

You will now find a file called `icing.war` in the `target` folder created by performing the package operation. This is the file you will be deploying.

Using the instructions for your app server (we are using Wildfly 10+ for development and testing), perform the following tasks:

1. Create a JPA data source with the JNDI name `java:/icing-media`

1. If using MySQL or MariaDB, perform the following database setup in the MySQL/MariaDB console (assuming your db is named `icing`):
	1. `ALTER DATABASE icing DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;`

1. Deploy the `icing.war` file to your app server using the instructions on its website (different for Wildfly/JBoss, Glassfish/Payara, etc.)

## Update

1. Stop icing if it is running

1. Change to the directory where it exists (for example: `$ cd ~/git/media`)

1. `$ ./icing.sh update` #This will update icing and run it again

