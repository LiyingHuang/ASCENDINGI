# ASCENDING
## Overview
### Project Technical Overview
this is a project...
1. Business Rule
    1.
    2.
   
2. Development Approaches
    1.
    2.
# Branch Informationopop
*


## Configure Local Environment 
### Set Up Local Database
Reference to Postgres docker [image]() for environment option
```
docker run --name ${PostgresContainerName} -e POSTGRES_USER=${username} -e POSTGRES_PASSWORD=${password} -e POSTGRES_DB=${databaseName} -p ${hostport}:${containerport} -d postgres
```
Refer to flyway setup [documentation](https://flywaydb.org/documentation/), find all [migration schema](src/main/resources/db/migrate)

