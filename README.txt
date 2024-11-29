solutions des Tps:
https://github.com/didier-mycontrib/java_8_9_1x et projets "new_features" , ...

==============

https://java.developpez.com/actu/318853/A-quel-point-Java-17-la-derniere-version-du-langage-est-il-plus-rapide-Voici-une-comparaison-avec-Java-11-et-Java-16/


===========
Pour les Tps sur les nouveautés de JAVA 21 , le référentiel git
est https://github.com/didier-mycontrib/java_new_21plus .

Le projet j21stable permet de tester les aspects stables (sans preview) de java 21.
TP à effectuer : compléter le code de la classe tp.java_new_21plus.loom.virtual_thread.SimpleCallsViaVirtualThreadApp 

Le projet j21preview permet d'expérimenter des aspects pas encore stabilisés .
affaire à suivre en java22,23,...

=======
exemple de projection JPA directe sur record:

public List<AdvocateNameRecord> 
	findAdvocateNamesByRegionTypedQuery(String region) {

	TypedQuery<AdvocateNameRecord> query = em.createQuery("""
			SELECT
			new com.bk.records.AdvocateNameRecord(a.fName, a.lName)
			FROM AdvocateEntity a
			WHERE region = :region
			""", AdvocateNameRecord.class);

	query.setParameter("region", region);

	return query.getResultList();
}


