FROM openjdk:8
COPY /target/outreach_auth_service-0.0.1-SNAPSHOT.jar /
EXPOSE 1111
CMD ["java","-jar","-Deureka.datacenter=cloud","-Dspring.profiles.active=aws","outreach_auth_service-0.0.1-SNAPSHOT.jar"]