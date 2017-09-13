#!/bin/bash

echo -e "Starting publish to Sonatype...\n"
./gradlew uploadArchives -PnexusUsername="${NEXUS_USERNAME}" -PnexusPassword="${NEXUS_PASSWORD}"
RETVAL=$?
if [ $RETVAL -eq 0 ]; then
  echo 'Completed publish!'
else
  echo 'Publish failed.'
  return 1
fi
