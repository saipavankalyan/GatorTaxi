JAVAC = javac
JAVA = java
sources = $(wildcard *.java)
classes = $(sources:.java=.class)

.PHONY: clean

all: $(classes)

$(classes): %.class: %.java
	$(JAVAC) $<

clean:
	rm -f *.class