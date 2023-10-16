This project contains 2 subprojects
* data-sharing-portal - This is the web portal application.
* file-processing-job - This is the file processing scheduled job.

### Setup
* data-sharing-portal - Go to /src/resources folder and make the following changes
    - Check and fill all the Database connection details (keys starts with 'spring.datasource') in the application.properties inside src/resources folder.
    - Make change in the property 'data.sharing.file.location' in the same application.properties file to point to the location where the portal application should store all the uploaded files.
    - Make change in the property 'data.sharing.app.showgranteddataindashboard' in the same application.properties file to display all granted private data in Dashboard tab in addition to the Granted Pvt Data tab.

* file-processing-job - Go to /src/resources folder and make the following changes
    - Check and fill all the Database connection details (keys starts with 'spring.datasource') in the application.properties inside src/resources folder.
    - Make change in the property 'file.processing.file.location' and 'file.processing.file.archivelocation' in the same application.properties file to point to the location where the job application should read and archive all the uploaded files.


### Build
* data-sharing-portal - Go to the folder /data-sharing-portal and run the following command
    - mvn clean package
* file-processing-job - Go to the folder /file-processing-job and run the following command
    - mvn clean package

### Run
* data-sharing-portal - Go to the folder /data-sharing-portal and run the following command
    - mvn spring-boot:run
* file-processing-job - Go to the folder /file-processing-job and run the following command
    - mvn spring-boot:run