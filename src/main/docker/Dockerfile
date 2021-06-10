FROM imix/base/docker-app:1.0.1
LABEL maintainer="nulay@mail.ru"

ARG appname=
ARG jarname=

ENV APPLICATION_NAME="${appname}" \
    APPLICATION_JAR="${jarname}"

COPY "maven/lib/application.jar" "/usr/local/lib/${jarname}"

VOLUME /var/lib/config

EXPOSE 80
