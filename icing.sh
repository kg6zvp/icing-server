#!/bin/bash

###########################################################
#                                                         #
# Package this file in the same directory as your         #
# project's output (media-swarm.jar) to run               #
# it, automatically creating a database file if necessary #
#                                                         #
###########################################################

function build_icing(){
	mvn -DskipTests clean package
}

# Check if a command has been provided
if ! [[ -z "$1" ]]; then
	case "$1" in
		update)
			git pull
			build_icing
			;;
		*)
			echo "Invalid command $1"
			;;
	esac
fi

# Check if the jar exists, build it if not
if [[ ! -f 'target/icing-swarm.jar' ]]; then
	echo "Icing jar not found. Building..."
	build_icing
fi

java -Xmx512m -jar target/icing-swarm.jar #-Dswarm.ds.connection.url=jdbc:h2:file:media-database;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=TRUE
