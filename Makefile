# binary search program makefile
# Hussein Suleman
# 27 March 2017

JAVAC=/usr/bin/javac
JAVA=/usr/bin/java
.SUFFIXES: .java .class
SRCDIR=src
BINDIR=bin

$(BINDIR)/%.class: $(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES2=GraphException.class Vertex.class Edge.class


CLASSES=$(CLASSES2:%.class=$(BINDIR)/%.class)


default: $(CLASSES)

run: $(CLASSES)
	$(JAVA) -Xmx1g -cp $(BINDIR) toktik

docs:
	javadoc -d doc/ src/*.java

clean:
	rm $(BINDIR)/*.class
