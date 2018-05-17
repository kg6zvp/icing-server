[![pipeline status](http://gitlab.mccollum.enterprises/icing/media/badges/master/pipeline.svg)](http://gitlab.mccollum.enterprises/icing/media/pipelines)

# Icing Node

## Install

### Prerequisites

- Java JDK 1.8 or higher
- Maven 3.3+ (recommend latest)
- Git (binary packages coming soon

#### Raspberry Pi

Install Fedora using the instructions found [here](https://fedoraproject.org/wiki/Architectures/ARM/Raspberry_Pi)

Follow the regular instructions for Fedora

#### Mac OS X

Assuming Java JDK 1.8 has already been installed, enter the following commands into a window of the `Terminal` application:

```bash
$ xcode-select --install
$ brew install maven
```

#### Fedora

```bash
$ sudo dnf install -y java-1.8.0-openjdk-devel maven git
```

#### CentOS

```bash
$ sudo yum install -y java-1.8.0-openjdk-devel maven git
```

#### Debian

```bash
$ sudo apt-get -y install openjdk-8-jdk maven git
```

#### Windows

Using the package manager [chocolatey](https://chocolatey.org/):

1. Run PowerShell or Command Prompt as an administrator
1. Run: `choco install maven git`

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

(this will create database files named `icing-database.h2.db` and `icing-database.trace.db` to store your data)

#### Windows

1. Open the command prompt
1. Use the `cd` command to change to the directory where icing was downloaded
1. Run with: `mvn wildfly-swarm:run`
1. For convenience, you could put this command into a `*.bat` file in that directory

### On App Server (like Wildfly)

Change to the directory (`$ cd ~/git/media`)

Create a war for deployment with:
`$ mvn clean -DskipTests package`

You will now find a file called `icing.war` in the `target` folder created by performing the package operation. This is the file you will be deploying.

Using the instructions for your app server (we are using Wildfly 10+ for development and testing), perform the following tasks:

1. Create a JPA data source with the JNDI name `java:/icing-media`

1. If using MySQL or MariaDB, perform the following database setup in the MySQL/MariaDB console (replace `icing` with the name of your database):
	1. `ALTER DATABASE icing DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;`

1. Deploy the `icing.war` file to your app server using the instructions on its website (different for Wildfly/JBoss, Glassfish/Payara, etc.)

## Update

1. Stop icing if it is running

1. Change to the directory where it exists (for example: `$ cd ~/git/media`)

1. `$ ./icing.sh update` #This will update icing and run it again

