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

akka:
	@$(PANDOC) -t html5 \
          --template=default.revealjs --standalone --section-divs \
          --variable theme="beige" --variable transition="linear" \
          docs/tut-out/akka.md -o docs/tut-out/akka.html
	@echo "- converting akka.md to akka.html"

akkahttp:
	@$(PANDOC) -t html5 \
          --template=default.revealjs --standalone --section-divs \
          --variable theme="beige" --variable transition="linear" \
          docs/tut-out/akkahttp.md -o docs/tut-out/akkahttp.html
	@echo "- converting akkahttp.md to akkahttp.html"

akkastreams:
	@$(PANDOC) -t html5 \
          --template=default.revealjs --standalone --section-divs \
          --variable theme="beige" --variable transition="linear" \
          docs/tut-out/akkastreams.md -o docs/tut-out/akkastreams.html
	@echo "- converting akkastreams.md to akkastreams.html"

kafka:
	@$(PANDOC) -t html5 \
          --template=default.revealjs --standalone --section-divs \
          --variable theme="beige" --variable transition="linear" \
          docs/tut-out/kafka.md -o docs/tut-out/kafka.html
	@echo "- converting kafka.md to kafka.html"


tut:
	$(SBT) docs/tut

all: tut scala play akka akkahttp akkastreams kafka

clean:
	rm -f docs/tut-out/*.html
