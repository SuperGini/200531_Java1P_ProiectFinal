# 200531_Java1P_ProiectFinal

Waring for the program to work you need to download this library: https://spaces.hightail.com/receive/b2BMj

The idea behind the program is to move the JLabel components inside a central JFrame where the JLabels act like normal view pages.
The database contains 3 tables: audit, flight, person. The requirments of the app are described in the Java 1 Professonal – proiect final.docx that are attached to this project.

Database: MySql

IDE: InteliJ IDEA


Database config:

database name:->>> proiect_final_java_1p
     
Tables:

audit:

	id: int -> primary key -> auto_increment

        username: varchar
	
        action: varchar
	
        date_time: varchar
	
        person_id: foreign_key -> id-ul de la person
        
person:

	id: -> int: primary key - > auto increment

        username: varchar
	
        email_adress: varchar
	
        password: varchar

flights:

	id: -> int: primary key -> auto_increment
		
        sursa: varchar
		
        destinatie: varchar
		
	oraPlecare: varchar
		
        oraSosire: varchar
		
        zile: varchar
		
        pret: double
	
	WARNING! All the pictures, gifs and icons are used for educational purposes and not for comercial use. If you
	don't want me to use them send mail to mihai.iordache82@gmail.com and i will remove them!
	
	https://unsplash.com/s/photos/plane?orientation=landscape - images
	http://www.pngall.com/


