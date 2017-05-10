# Exempel efter
* http://www.mastertheboss.com/jboss-frameworks/maven-tutorials/maven-hibernate-jpa/maven-and-jpa-tutorial
* Men det mesta omgjort nu!

# Skapades f.b. med
mvn archetype:generate -DarchetypeGroupId=com.github.lalyos -DarchetypeArtifactId=standalone-jpa-eclipselink-archetype -DgroupId=com.mastertheboss -DartifactId=EclipseJPAExample -Dversion=1.0-SNAPSHOT -Dpackage=com.mastertheboss -DinteractiveMode=false

# Rensa databasen
* Enklast är att ta bort katalogen simpleDb
* Detta behöver också göras om man ändrar signaturerna på entiteterna. Dvs lägger till eller tar bort fält.