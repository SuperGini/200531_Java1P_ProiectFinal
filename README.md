200531_Java1P_ProiectFinal
Atentie! pentru ca programul sa functioneze trebuie sa folositi o biblioteca externa pe care o gasiti
aici: https://spaces.hightail.com/receive/b2BMj

Programul “Proiect final Java 1P” se bazeaza in principal pe miscarea unor componente JLabel in interiorul si exeriorul unui JFrame Central. 

Programul se compune din 2 JFrame-uri principale:

1.	LoadingFrame  - contine 2 JLabel- principale.
1.	LoadingPage -> porneste la aparea butonului verde. Dupa aproximativ 10 secunde acest label sedeplaseaza in partea superioara lasand sa se vada StartPage-ul.
2.	StartPage -> in functie de butonul pe care il selectam “login” sau “register page” ne va trimite catre pagina respective din CentralFrame.
         2.  CentralFrame – contine 7 JLabel principale.
1. LoginPage si RegisterPage – sunt pozitionate initial deasupra CentralFrame. In momentul in care     din StartPage e selectat login sau register page coboara JLabel-ul respective. Miscarea acestor label-uri se face cu ajutorul bibliotecii Animation class (vezi link la inceput).
2. HomePage, AddFlightPage, MyAccountPage, ChangePasswordPage, AuditPage sunt pozitionate in partea inferioara a CentralFrame. In functie de butoanele selectate si in functie de cerintele programului acestea se misca in sus si in jos facandu-se astfel trecerea de la o pagina la alta.


Programul este gandit in jurul acestor JLabel-uri care tin locul de JFrame-uri.	

Configurare baza de date:

Nume baza de date:->>> proiect_final_java_1p
     
Baza de date contine 3 tabele: audit, flight, person.

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


