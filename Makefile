NAME ?= transactions-enrichment-service

mvn-build:
	mvn clean install

docker-build: mvn-build
	@minikube status >/dev/null || echo "Minikube is stopped"
	@eval $$(minikube docker-env) ;\
	docker build --no-cache -t $(NAME) .

dev: docker-build
	@pip3 install --user -r scripts/requirements.txt > /dev/null
	@python3 scripts/deploy.py -n $(NAME)
