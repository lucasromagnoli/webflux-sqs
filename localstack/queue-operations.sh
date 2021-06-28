# sudo docker-compose up
print "Verificando se o localstack está up e com o serviço do SQS funcionando."
curl http://localhost:4566/health
print "Criando fila teste-001"
aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name teste-001
print "Listando as filas"
aws --endpoint-url=http://localhost:4566 sqs list-queues
print "Criando uma menasgem na fila"
aws --endpoint-url=http://localhost:4566 sqs send-message --queue-url http://localhost:4566/000000000000/teste-001 --message-body 'Mensagem de teste. Hello SQS'
print "Recebendo mensagem da fila"
aws --endpoint-url=http://localhost:4566 sqs receive-message --queue-url http://localhost:4566/000000000000/teste-001
print "Limpando todas as mensanges de uma fila"
aws --endpoint-url=http://localhost:4566 sqs purge-queue --queue-url http://localhost:4566/000000000000/teste-001
print "Deletando a fila"
aws --endpoint-url=http://localhost:4566 sqs delete-queue --queue-url http://localhost:4566/000000000000/teste-001
