Prerequisites:
1. JDK 11+
2. Docker 20+

Docker execution
1. git clone https://github.com/tomaspascual/challengeone.git
2. mvn package -Dmaven.test.skip (tests not ready yet)
3. docker run -p 8080:8080 wabi/challengeone:0.0.1-SNAPSHOT (please don't run this application on Docker yet as it's being configured, not completed yet, to be running in Heroku)
4. ExecutionController RestController contains all the runnable examples

Technical questions:
1. SSR_v2.pdf (SSr profile)