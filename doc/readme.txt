Please following the steps to install ViMonitor project:
1.Install PostgreSQL8.4, if you already have it installed, just ignore.
1.1Create a database named as vimonitor with username/password wereach/reach1234.
1.2Run /sql/postgre8.4.sql 
2.Install JBoss4.4.2GA,and set environment variable JBOSS_HOME to JBoss home directory.
http://sourceforge.net/project/showfiles.php?group_id=22866&package_id=16942&release_id=548923
3.Deploy ViMonitor to JBoss
There are two ways to deploy the project.
3.1Run ant to deploy with command:ant clean-deploy
3.2Also you could follow to manually deploy to JBoss
(1)Copy  /build/vimonitor.ear to JBOSS_HOME/server/default/deploy
(2)Copy  /conf/postgres-ds.xml(which is PostgreSQL database info for JBoss) to JBOSS_HOME/server/default/deploy
(3)Copy jar files under /lib  to JBOSS_HOME/server/default/lib
4.Start JBOSS_HOME/bin/run.bat.



