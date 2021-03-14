FROM ubuntu:18.04
# Arguments
ARG USER=hyperledger
ARG VOLUME=/home/volume
ARG SHELL=/bin/bash

RUN apt-get update

# Install OS utilities
RUN apt-get install -y sudo

RUN tmppwd=$PWD
#Utils
RUN echo "####################################################### "
RUN echo "#INSTALLING UTILS# "
RUN echo "####################################################### "
#unzip
RUN sudo apt install unzip -y
#wget
RUN sudo apt install wget  -y
#wget
RUN sudo apt install git -y
#tree
RUN sudo apt install tree -y
#telnet
RUN sudo apt-get install telnetd -y
#httping
RUN sudo apt-get update -y
RUN sudo apt-get install -y httping
#tcpping
RUN sudo apt-get install tcptraceroute -y
RUN cd /usr/bin/
RUN wget http://pingpros.com/pub/tcpping
RUN chmod 755 tcpping
# RUN sudo apt-get install hping3 -y

#JAVA
RUN echo "####################################################### "
RUN echo "#                   INSTALLING JAVA                  # "
RUN echo "####################################################### "r
RUN sudo apt install openjdk-8-jre-headless -y
RUN echo 'export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64' >> ~/.bashrc
RUN echo 'export JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64"' >> ~/.profile
RUN echo "Java version ..."
RUN java -version

#MAVEN
RUN echo "        ####################################################### "
RUN echo "        #                   INSTALLING MAVEN                  # "
RUN echo "        ####################################################### "
RUN sudo mkdir -p /opt/apache-maven/
RUN cd /tmp
RUN wget https://downloads.apache.org/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.zip
RUN unzip apache-maven-3.6.3-bin.zip
RUN sudo mv apache-maven-3.6.3/* /opt/apache-maven/
RUN echo 'export M2_HOME=/opt/apache-maven' >> ~/.bashrc
RUN echo 'export PATH=${M2_HOME}/bin:${PATH}' >> ~/.bashrc

RUN echo 'export M2_HOME=/opt/apache-maven' >> ~/.profile
RUN echo 'export PATH=${M2_HOME}/bin:${PATH}' >> ~/.profile
RUN echo "MAVEN version ..."
#mvn -v

#NODE
RUN echo "        ####################################################### "
RUN echo "        #                   INSTALLING NODE                  # "
RUN echo "        ####################################################### "

#  Node.js 8.x LTS Carbon is no longer actively supported!
RUN curl -sL https://deb.nodesource.com/setup_10.x | sudo bash -
RUN sudo apt install nodejs -y
RUN echo "NODE version ..."
RUN node --version

#GO
RUN echo "        ####################################################### "
RUN echo "        #                   INSTALLING GOLANG                  # "
RUN echo "        ####################################################### "
RUN sudo apt update -y
RUN sudo apt install curl -y
RUN cd /tmp
RUN sudo curl -O https://dl.google.com/go/go1.12.linux-amd64.tar.gz

RUN sudo tar -xvf go1.12.linux-amd64.tar.gz
RUN sudo mv go /usr/local
RUN chmod +rwx ~/.profile
RUN echo 'export PATH=$PATH:/usr/local/go/bin:/usr/local/bin' >> ~/.bashrc
RUN echo 'export PATH=$PATH:/usr/local/go/bin:/usr/local/bin' >> ~/.profile
RUN ~/.profile
RUN echo ~/.profile
RUN echo "GOLANG version ..."
# RUN go version ojo quitar

#GOPATH Directory
RUN cd $HOME
RUN mkdir go
RUN echo 'export GOPATH=$HOME/go' >> ~/.profile
RUN ~/.profile

RUN echo 'export PATH=$PATH:$GOPATH/bin' >> ~/.profile
RUN ~/.profile

#FABRIC
# Ejecuta el setup inicial de las maquinas , creación de directorios y descarga de imagenes de Hyperledger Fabric
RUN echo "        ####################################################### "
RUN echo "        #                   INSTALLING HYPERLEDGER FABRIC     # "
RUN echo "        ####################################################### "
RUN cd $tmppwd
RUN FABRICSamplesDir="$HOME/hyperledger/fabric"
RUN mkdir -p '$FABRICSamplesDir'

RUN sudo chmod -R 777 '$FABRICSamplesDir'
RUN cd '$FABRICSamplesDir'
RUN sudo curl -sSL http://bit.ly/2ysbOFE | bash -s 2.2.0

RUN echo 'export PATH=$PATH:$HOME/hyperledger/fabric/fabric-samples/bin' >> ~/.profile
RUN ~/.profile

# Set up non root user
RUN echo "$USER ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers
RUN useradd -ms $SHELL $USER
RUN mkdir -p $VOLUME
RUN chown -R $USER:$USER $VOLUME

# # Preserve bash history
# RUN SNIPPET="export PROMPT_COMMAND='history -a' && export HISTFILE=/commandhistory/.bash_history" \
#     && echo $SNIPPET >> "/root/.bashrc" \
#     && mkdir /commandhistory \
#     && touch /commandhistory/.bash_history \
#     && chown -R $USER /commandhistory \
#     && echo $SNIPPET >> "/home/$USER/.bashrc"

# USER $USER
