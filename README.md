# Restful-Jersey-example
## Description
In this source code I will show you how to implement **RestFul** with **Jersey** in Java. I also implemented **JWT** for the authentication. Whether this code helped you, please donate for a coffee. Thanks.

[![paypal](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=AFSV8TQBVW6LC)

## Instruction
Remember to run **maven** to install the **dependencies**.

## Structure
* usermanagment.sql -> MySql DB
* users.cvs -> A record of users.
* src
    * user
        * managment
            * crud
                * CrudMethod.java
            * db
                * DBConnection.java
                * dao
                    UserDao.java
                * query
                    * Query.java
            * email
                * SendForgottenPassword.java
            * filter
                * JWTTokenNeeded.java
                * JWTTokenNeededFilter.java
                * ResponseFilter.java
            * model
                * Error.java
                * User.java
            * security
                * Aes256.java
                * Sha256.java
            * service
                * AplicationApi.java
                * UserService.java
                * exception
                    * mapper
                        * GenericExceptionMapper.java
                        * ValidationExceptionMapper.java

## Dependecies of this project
```xml
<dependencies>
		<!-- https://mvnrepository.com/artifact/org.glassfish.jersey.containers/jersey-container-servlet -->
		<dependency>
			<groupId>org.glassfish.jersey.containers</groupId>
			<artifactId>jersey-container-servlet</artifactId>
			<version>2.25.1</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.glassfish.jersey.inject/jersey-hk2 -->
		<dependency>
			<groupId>org.glassfish.jersey.inject</groupId>
			<artifactId>jersey-hk2</artifactId>
			<version>2.26</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.glassfish.jersey.media/jersey-media-moxy -->
		<!-- <dependency>
			<groupId>org.glassfish.jersey.media</groupId>
			<artifactId>jersey-media-moxy</artifactId>
			<version>2.26</version>
		</dependency>-->
		 <dependency>
            <groupId>org.glassfish.jersey.media</groupId>
            <artifactId>jersey-media-json-jackson</artifactId>
            <version>2.25.1</version>
        </dependency>
		<!-- https://mvnrepository.com/artifact/org.glassfish.jersey.ext/jersey-bean-validation -->
		<dependency>
			<groupId>org.glassfish.jersey.ext</groupId>
			<artifactId>jersey-bean-validation</artifactId>
			<version>2.25.1</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.glassfish.jersey.test-framework.providers/jersey-test-framework-provider-jetty -->
		<dependency>
			<groupId>org.glassfish.jersey.test-framework.providers</groupId>
			<artifactId>jersey-test-framework-provider-jetty</artifactId>
			<version>2.25.1</version>
			<scope>test</scope>
		</dependency>
		<!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt -->
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt</artifactId>
			<version>0.7.0</version>
		</dependency>
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>5.1.38</version>
		</dependency>
		<dependency>
			<groupId>commons-codec</groupId>
			<artifactId>commons-codec</artifactId>
			<version>1.10</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/javax.mail/mail -->
		<dependency>
			<groupId>javax.mail</groupId>
			<artifactId>mail</artifactId>
			<version>1.4.7</version>
		</dependency>
		<dependency>
			<groupId>org.apache.commons</groupId>
			<artifactId>commons-dbcp2</artifactId>
			<version>2.1.1</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/junit/junit -->
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.12</version>
			<scope>test</scope>
		</dependency>
	</dependencies>
```

## Demonstration
![CKEditor](https://raw.githubusercontent.com/JoanVasquez/restful-jersey-example/master/1.PNG)
![CKEditor](https://raw.githubusercontent.com/JoanVasquez/restful-jersey-example/master/2.PNG)
![CKEditor](https://raw.githubusercontent.com/JoanVasquez/restful-jersey-example/master/3.PNG)