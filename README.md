# App Java Cliente Servidor

## Comenzando 🚀

Client server application that makes use of the FTP protocols for sending and downloading files and SMTP / POP3 for sending and receiving emails.

### Pre-requisitos 📋

The program is configured to start the client and the server on the same computer, for this we can see how we instantiate both the client and the server in the Main of the project. To make it work remotely, we must comment on one of the calls depending on whether you want to act as client and server and change the client's connection IP, which is LOCALHOST or 127.0.0.1 to the corresponding server IP.

The program is only functional with hotmail and gmail domain emails

For the correct functioning of the STMP and POP3 protocols, you must carry out some minimal configurations, below you have some links where the process is detailed

```
GMAIL : https://support.google.com/mail/answer/7126229?hl=es
HOTMAIL : https://es.ccm.net/faq/16658-outlook-conectar-dispositivos-y-aplicaciones-con-pop
```

## Deployment 📦

Import the project directly into Eclipse, the executable will be ready soon!

## Building in 🛠️

BACKEND LIBRARIES
* [JxBrowser](https://mvnrepository.com/artifact/jxbrowser/jxbrowser-win/) - Frontend development
* [Maven](https://maven.apache.org/) - Dependency manager
* [H2 Database](https://mvnrepository.com/artifact/com.h2database/h2/) - Database used
* [JavaMail](https://mvnrepository.com/artifact/javax.mail/mail) - Mail handling

FRONTEND LIBRARIES
* [Boostrap](https://mvnrepository.com/artifact/org.webjars/bootsrap)
* [JQuery](https://mvnrepository.com/artifact/org.webjars/jquery)
* [Popper](https://mvnrepository.com/artifact/org.webjars.bower/popper.js)
* [JSoup](https://mvnrepository.com/artifact/org.jsoup/jsoup)

## Autores ✒️

* **Rafael Valls Sánchez** 
* **Guillermo García Barcias** - *Trabajo Inicial* - [Linkedin](https://www.linkedin.com/in/guillermo-barcia-molina-311b3a167/)

## Licencia 📄

De uso libre

## Manual de Usuario

## Login

In order to start a session we must Register, for this we must first click on the Register button


![imglogin](https://user-images.githubusercontent.com/23072249/51788269-21e7a000-217c-11e9-93cb-5f11e91b3b82.png)


## Registrarse

In the next window we must enter the registration data, which must coincide with the email data


![imgreg](https://user-images.githubusercontent.com/23072249/51787814-bb13b800-2176-11e9-9b41-c89466425bef.png)

## Menú

With the arrows you can select what action to carry out: upload file, download file or access email


![imgmenu](https://user-images.githubusercontent.com/23072249/51787824-dd0d3a80-2176-11e9-83e1-5fafe60f8106.png)

## Subir Archivo

In this case we will access the option to upload file, for this we look for and click this icon in large. In the next window we must Click on Select file


![imgsubirarchivo](https://user-images.githubusercontent.com/23072249/51787839-0201ad80-2177-11e9-91bc-2ebe957fb258.png)

This will display the following window in which we can select any file from our computer to upload it to the FTP server


![imgsubirarchivo2](https://user-images.githubusercontent.com/23072249/51787855-1c3b8b80-2177-11e9-976d-1d737a4cc891.png)

We select open and send


![imgsubirarchivo3](https://user-images.githubusercontent.com/23072249/51787866-2cec0180-2177-11e9-892d-91c248e26b04.png)

When the loading bar is complete, it means that our file has been uploaded correctly and we can click on "Exit" to return to the menu.

## Descargar Archivo

Then we will access the option to upload file, for this we look for and click this icon in large


![imgbajar](https://user-images.githubusercontent.com/23072249/51787874-5573fb80-2177-11e9-88b6-26f9446cf714.png)

Once our window is displayed we can see all the files hosted on our ftp server


![imgbajar2](https://user-images.githubusercontent.com/23072249/51787881-6886cb80-2177-11e9-8db8-09fca93f04fc.png)

If we click on any of them, the download will start


![imgbajar3](https://user-images.githubusercontent.com/23072249/51787891-7b010500-2177-11e9-971d-1203f8445b1a.png)

## SMTP Y POP

Finally we are going to access the email option, for this we must press the following icon


![imgcorreo](https://user-images.githubusercontent.com/23072249/51787897-8c4a1180-2177-11e9-8ec2-abd5a9551c32.png)

If we click, the following window will be displayed, in which all available emails will be automatically loaded


![imgcorreo2](https://user-images.githubusercontent.com/23072249/51787919-9f5ce180-2177-11e9-8022-2786e9142bf2.png)

## Enviar Correo

For this we must click on the bars located in the upper left corner which will display the following options


![imgcorreo3](https://user-images.githubusercontent.com/23072249/51787929-b7ccfc00-2177-11e9-9be9-df6380b427af.png)

Once the window is loaded, we must fill in the fields and click to send the corresponding message


![imgcorreo4](https://user-images.githubusercontent.com/23072249/51787935-d3380700-2177-11e9-9a65-792ee0820d18.png)

