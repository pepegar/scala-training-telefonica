.PHONY: tut clean

PANDOC = pandoc
SBT = sbt

scala:
	@$(PANDOC) -t html5 \
          --template=default.revealjs --standalone --section-divs \
          --variable theme="beige" --variable transition="linear" \
          docs/tut-out/scala.md -o docs/tut-out/scala.html
	@echo "- converting scala.md to scala.html"

play:
	@$(PANDOC) -t html5 \
          --template=default.revealjs --standalone --section-divs \
          --variable theme="beige" --variable transition="linear" \
          docs/tut-out/play.md -o docs/tut-out/play.html
	@echo "- converting play.md to play.html"


tut:
	$(SBT) docs/tut

all: tut scala play

clean:
	rm -f docs/tut-out/*.html
