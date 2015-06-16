MI-RUN 2014/2015 (zimni semestr)

Semestralni prace 

=Autori=

Adam Cervenka (cerveada@fit.cvut.cz) & Lubos Palisek (palislub@fit.cvut.cz)


=Struktura archivu=

/btree - zdrojove kody + bytecode ulohy (B-strom)
	./java/lang/* - tridy z classpath
	./BTree.class - bytecode BTree.java
	./BTree.java - implementace B-stromu
	./BTreeMain.class - bytecode BTreeMain.java
	./BTreeMain.java - trida s main() metodou pro spusteni B-stromu
	./Node.class - bytecode Node.java 
	./Node.java - implementace uzlu stromu
/src - zdrojove kody naseho VM
	./main/java/cz/fit/cvut/bvm/classloading/* - tridy zajistujici nacitani classfile
	./main/java/cz/fit/cvut/bvm/exceptions/* - vyjjimky naseho VM
	./main/java/cz/fit/cvut/bvm/garbagecollector/* - tridy implementujici garbage collector
	./main/java/cz/fit/cvut/bvm/structures/* - tridy reprezentujici interni strukturty
	./main/java/cz/fit/cvut/bvm/utils/* - pomocne tridy s 
	./main/java/cz/fit/cvut/bvm/BestVirtualMachine.java - trida implementujici zakladni chovani VM
	./main/java/cz/fit/cvut/bvm/Main.java - trida s main() metodou
	./test/java/cz/fit/cvut/bvm/structures/* - testy pro instrukce
/input-data.txt - vstupni data pro test nacitani dat z externiho souboru
/pom.xml - maven POM pro build
/readme.txt - tento soubor :-)


=Jak zkompilovat semestralni praci=

mvn package


=Spusteni programu=

java -jar bvm-1.0.0-SNAPSHOT.jar "<absolutni cesta ke zkompilovane tride s main() metodou>" "arg0"(nepovinny) "arg1"(nepovinny) "arg2"(nepovinny)"


=Popis semestralni prace=

Vysledkem teto semestralni prace je program, ktery umi nacist a provest instrukce zkompilovaneho kodu pro programovaci jazyk Java 7 (pomoci javac compileru).
Virtualni stroj ma implementovane vsechny aritmeticke operace i ridici operace povolene v jazyku Java 7.
Vyjjimky jsou implementovany stejne jako v klasicke Jave.
Pamet pro objekty je spravovana garbage collectorem, ktery vyuziva algoritmus mark-and-sweep.
//TODO popis nativnich metod