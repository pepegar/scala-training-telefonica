.PHONY: tut clean

PANDOC = pandoc
SBT = sbt

basics1:
	@$(PANDOC) -t html5 \
          --template=default.revealjs --standalone --section-divs \
          --variable theme="beige" --variable transition="linear" \
          docs/tut-out/basics1.md -o docs/tut-out/basics1.html
	@echo "- converting basics1.md to basics1.html"


tut:
	$(SBT) docs/tut

all: tut basics1

clean:
	rm -f docs/tut-out/*.html
