![](https://logos-download.com/wp-content/uploads/2020/06/Sovos_Logo.png)
# 
# receipt-status-pending-util

### Features

- Corrige documentos "**Boletas**" generados por EGWBO que llegan al Eserver despues de que llega el estado asociado al SII, el cual se registra en la tabla **receiptstatuspending**, finalmente el documento queda sin su estado en las tablas docemistate y docemilog.

### Funcionality

#####Egateway
- Una vez el documento **Boleta** es transportado al Eserver por el agente **MixtoTransporte** este documento es registrado en la tabla **incpkgrec**

#####Eserver
- Luego el agente **MixtoReception** va a leer esta tabla y obtiene el documento, posterior a ello realiza los insert en las tablas docemi, docemistate, docemilog y docemidigest.

#####ReceiptStatus.log
- Agente encargado de ir a buscar a los Microservicios los estados correspondientes  a las **Boletas**  luego consulta por los documentos en la tabla docemi y actualiza los estados segun corresponde, en caso de no encontrar documentos registra la informacion en la tabla **receiptstatuspending**.
 
#### receipt-status-pending-util
- El utilitario es un servicio REST el cual disponibiliza dos endpoints 

				Metodo all-companies 
	Este metodo se encarga de procesar todos los registros que se encuentran en la tabla receiptstatuspending, es decir no hace ningun filtro y se trae todo lo que encuentre, hace una sola pasada .
				Metodo by-companyId
	Este metodo se encarga de procesar los registros de la tabla receiptstatuspending filtrando por empresa, para ello solo deben ingresar el coid de la empresa

	para la ejecución de los endpoints anteriores se pueden ejecutar desde la documentacion de **Swagger**, desde **Postman** o utilizando **curl**:
	
- [ ] Swagger => http://IP:PUERTO/swagger-ui.html   

- [ ] Postman => https://www.postman.com/

- [ ] curl  =>

```java
curl -X PUT --header 'Content-Type: application/json' --header 'Accept: application/json' 'http://localhost:8888/app/v1/process/all-companies'


curl -X PUT --header 'Content-Type: application/json' --header 'Accept: application/json' 'http://localhost:8888/app/v1/process/10'```


# 
# Execution
- Para ejecutar el utilitario se debe contar con el jar **"receipt-status-pending-util-0.0.1-SNAPSHOT.jar"** y el archivo **application.properties**
- Primero debemos tener seteado correctamente el archivo application.properties
```java
spring.application.name=receipt-status-pending-util
server.port=8888
#####DATA BASE
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/ppldte
spring.datasource.username=ppl
spring.datasource.password=ppl123
#####LOGBACK
logging.level.org.springframework.web=INFO
logging.level.org.hibernate=INFO
logging.file.name=${spring.application.name}
logging.file.path=logs/
#####QUERY PARAMETER
####### maximo de registros que traera por consulta
query.parameter.max-rows=5
####### formato [ yyyy-MM-dd HH:mm:ss ] ejemplo hasta el 2021-03-17 23:59:59
####### la consulta sera select * from receiptstatuspending where rspsiisenttime < '2021-03-17 23:59:59' 
query.parameter.end-date=2021-04-21 23:59:59
```
- Luego se debe ejecutar el jar de la siguiente forma
````java
java -jar receipt-status-pending-util-0.0.1-SNAPSHOT.jar
````
