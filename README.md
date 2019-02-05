# fnt_nba_players
fantasy basketball project


How to run on local machine

git clone https://github.com/omertopal/fnt_nba_players.git

git checkout master

Main class to run application
@SpringBootApplication
public class MyApplication   

standart calculation Rest Call
http://localhost:8084/nba/calc/calcUsage/STD

OPTA calculation Rest Call
http://localhost:8084/nba/calc/calcUsage/OPTA

H2 DB console
http://localhost:8084/nba/h2

Driver Class : org.h2.Driver	
JDBC URL	 : jdbc:h2:mem:testdb
User Name	 : sa

SELECT * FROM PLAYERS  WHERE IS_MY =1

The result returns from this sql is the players you have on your team.
the algorithm will calculate the best permutation for these set of players.

Every player will be placed to slots (PG,SG,SF,PF,C,UTIL) according to their skills, game dates and team schedules


