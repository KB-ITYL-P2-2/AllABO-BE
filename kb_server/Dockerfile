# Step 1: 베이스 이미지를 지정합니다 (Tomcat 9).
FROM tomcat:9.0-jdk17

# Step 2: 컨테이너에 환경 변수를 설정합니다.
ENV JAVA_OPTS="-Xms512m -Xmx1024m"

# Step 3: WAR 파일을 Tomcat 웹 애플리케이션 디렉토리에 복사합니다.
# Spring 프로젝트의 build 경로 또는 war 파일 위치를 확인하여 경로 설정
COPY build/libs/kb_server-1.0-SNAPSHOT.war /usr/localv/tomcat/webapps/ROOT.war

# 수정된 server.xml을 Tomcat 설정 디렉토리에 복사
COPY ./server.xml /usr/local/tomcat/conf/server.xml


# Step 4: 컨테이너가 시작되면 Tomcat이 자동으로 실행됩니다.
EXPOSE 8888

# Step 5: Tomcat 서버 실행 명령어 (기본 설정으로 이미 포함되어 있음)
CMD ["catalina.sh", "run"]
