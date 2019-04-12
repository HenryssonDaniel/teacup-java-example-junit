# teacup-java-example-junit
Java **Te**sting Fr**a**mework for **C**omm**u**nication **P**rotocols and Web Services examples with JUnit

[![Build Status](https://travis-ci.com/HenryssonDaniel/teacup-java-example-junit.svg?branch=master)](https://travis-ci.com/HenryssonDaniel/teacup-java-example-junit)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=HenryssonDaniel_teacup-java-example-junit&metric=alert_status)](https://sonarcloud.io/dashboard?id=HenryssonDaniel_teacup-java-example-junit)
## What ##
This repository contains examples using the JUnit test engine.
## Why ##
It can be used as a reference to set up and write your first test case.
## How ##
### Add the dependencies ###
It has three dependencies:
1. Core (io.github.henryssondaniel.teacup:core)
1. JUnit test engine (io.github.henryssondaniel.teacup.engine:junit)
1. HTTP protocol (io.github.henryssondaniel.teacup.protocol:http)
### Add the test engine ###
1. Create a file named io.github.henryssondaniel.junit.platform.engine.TestEngine in src/main/resources/META-INF/services
1. Add the content io.github.henryssondaniel.teacup.engine.junit.TeacupTestEngine
1. Add the engine to the build file, this is different depending on the build tool you are using.  
The best thing is to check: https://junit.org/junit5/docs/current/user-guide/#running-tests-build
### Write your test (pseudocode) ###
Writing your test can be divided into three steps:
1. response = client.send(request) // send the request
2. node = responseBuilder.setStatusCode(statusCode).setVersion(version).build() // define the assertions
3. node.verify(response) // verify

All assertions can be chained and all setters take an assertion rather than a value.  
This means that statusCode and version represents assertions as well. The code could look similar to this:  
statusCode = integerBuilder.isLessThan(300).isGreaterThanOrEqualTo(200).build()  
version = versionBuilder.isSameAs(Version.2).build()