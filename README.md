# ecommerce-app
use of micro-services

## Main Requirements
1. Based on micro services architecture, design and implement an ecommerce-based application
2. The solution is capable to handle at least 2500 request OR transaction per second

## Overall Solution Design
Prepare an application solution OR software solution/conceptual design for the requirements. Focus on the core modules in your solution. You are allowed to make any appropriate assumption
Example: Persistent storage is not a core module in the solution. You can make assumption that persistent storage is always hosted on cloud, and this will complete the story in solution design if persistent storage is required in your design

## Expectations/Devlierables
Our expectations are below:
1. Project should be hosted in Git repository, either hosted on public GitHub or local git repository. This is mandatory
2. Demonstrate how to build, run, and deploy your application. Walk thru the end-to-end flow. This is mandatory
- Note: Unit test is optional
3. Focus on application implementation. Demonstrate, and explain your solution implementation. This is mandatory
4. Propose future enhancement(s) on the current solution
5. Explain the implemented algorithm if there is any
6. Use docker for deployment, if possible

## Distributed Tracing
Using Micrometer and Zipkin
`docker run -d -p 9411 openzipkin/zipkin:latest`

## Authentication
'docker run -p 8181:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:21.0.1 start-dev`

