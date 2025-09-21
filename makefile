SERVERS = colab4.AcceptorServer

run-servers-old:
	cd /workspaces/ds-colab-4 ; /usr/bin/env /opt/java/11.0.14/bin/java @/tmp/cp_aole26plbrp4npc4i4z0rfm4f.argfile colab4.AcceptorServer 9001 &
	cd /workspaces/ds-colab-4 ; /usr/bin/env /opt/java/11.0.14/bin/java @/tmp/cp_aole26plbrp4npc4i4z0rfm4f.argfile colab4.AcceptorServer 9002 &
	cd /workspaces/ds-colab-4 ; /usr/bin/env /opt/java/11.0.14/bin/java @/tmp/cp_aole26plbrp4npc4i4z0rfm4f.argfile colab4.AcceptorServer 9003 &
	cd /workspaces/ds-colab-4 ; /usr/bin/env /opt/java/11.0.14/bin/java @/tmp/cp_aole26plbrp4npc4i4z0rfm4f.argfile colab4.AcceptorServer 9004 &
	cd /workspaces/ds-colab-4 ; /usr/bin/env /opt/java/11.0.14/bin/java @/tmp/cp_aole26plbrp4npc4i4z0rfm4f.argfile colab4.AcceptorServer 9005 &

run-servers:
	java -cp ./target/classes:./lib/* colab4.AcceptorServer 9001 &
	java -cp ./target/classes:./lib/* colab4.AcceptorServer 9002 &
	java -cp ./target/classes:./lib/* colab4.AcceptorServer 9003 &
	java -cp ./target/classes:./lib/* colab4.AcceptorServer 9004 &
	java -cp ./target/classes:./lib/* colab4.AcceptorServer 9005 &

run-demo:
	java -cp ./target/classes:./lib/* colab4.Demo 

kill:
	@pkill -f '$(SERVERS)' || true