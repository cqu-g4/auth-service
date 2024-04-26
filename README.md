We are using Keycloak authorization server.

We can use Docker for that purpose. Following is the docker command:
```
docker run -d -p 7080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:24.0.3 start-dev
```

PS: make sure to expose the port to 7080. If other port is exposed, then change the port number cofiguration file.

We need to change the keycloak configuration like clientId and clientSecret value as well. 
