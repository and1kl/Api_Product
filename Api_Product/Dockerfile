FROM openjdk:8
ADD target/Api_Product-*.jar apiproduct.jar
EXPOSE 8087
ENTRYPOINT ["java","-jar", "apiproduct.jar"]