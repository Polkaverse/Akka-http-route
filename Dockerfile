FROM openjdk:8-jre-alpine

COPY ./target/scala-2.12/Akka-Http-Crud-assembly-1.0.jar	 ./Pankaj

CMD java -jar Akka-Http-Crud-assembly-1.0.jar
