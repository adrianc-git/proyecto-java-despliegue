# Usamos Tomcat 10 (Servidor Web Java)
FROM tomcat:10.1-jdk17

# 1. Descargamos el driver de MariaDB para que Java sepa hablar con la BD
ADD https://repo1.maven.org/maven2/org/mariadb/jdbc/mariadb-java-client/3.1.4/mariadb-java-client-3.1.4.jar /usr/local/tomcat/lib/

# 2. Preparamos las carpetas
WORKDIR /usr/local/tomcat/webapps/ROOT
RUN mkdir -p WEB-INF/classes/com/ejemplo

# 3. Copiamos los archivos
COPY index.html .
COPY src/InsertarUsuarioServlet.java ./src/

# 4. COMPILAMOS EL JAVA (El paso crítico)
# Esto convierte tu .java en un .class ejecutable
RUN javac -cp /usr/local/tomcat/lib/servlet-api.jar:/usr/local/tomcat/lib/mariadb-java-client-3.1.4.jar \
    -d WEB-INF/classes \
    src/InsertarUsuarioServlet.java

EXPOSE 8080