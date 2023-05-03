# binary search program makefile

JFLAGS = -g
JC = javac
SRCDIR= src
BINDIR= bin
DOCDIR = doc

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) -cp $(BINDIR) $*.java -d $(BINDIR)

CLASSES = $(SRCDIR)/*.java

default: classes

classes:
	$(JC) $(JFLAGS) -cp $(BINDIR) $(CLASSES) -d $(BINDIR)

clean:
	$(RM) $(BINDIR)/*.class

run:
	java -cp $(BINDIR) Graph

javadoc:
	javadoc -d $(DOCDIR) $(SRCDIR)/*.java