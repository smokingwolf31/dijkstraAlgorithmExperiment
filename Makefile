JFLAGS = -g
JaC = javac
SRCDIR= src
BINDIR= bin
DOCDIR = doc
DATADIR = data

.SUFFIXES: .java .class
.java.class:
	$(JaC) $(JFLAGS) -cp $(BINDIR) $*.java -d $(BINDIR)

CLASSES = $(SRCDIR)/*.java

default: classes

classes:
	$(JaC) $(JFLAGS) -cp $(BINDIR) $(CLASSES) -d $(BINDIR)

clean:
	$(RM) $(BINDIR)/*.class

run:
	java -cp $(BINDIR) Graph

docs:
	javadoc -d $(DOCDIR) $(SRCDIR)/*.java