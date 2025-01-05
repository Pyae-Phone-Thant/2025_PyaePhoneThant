In this repository, both backend project and frontend project are included.

For frontend project,to start the server run "npm start".

For backend project, if you prefer to run the Spring Boot backend in a Docker container, follow these steps:
Build the Docker image for the backend:
"docker build -t springboot-backend ."

Run the backend in Docker:
"docker run -p 8080:8080 springboot-backend"
