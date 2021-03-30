echo off
for /f %%i in ('docker network ls --filter name^=sdnet ^| find /c /v ""') do set RESULT=%%i

if NOT %RESULT%==2 docker network create --driver=bridge --subnet=172.20.0.0/16 sdnet

set argC=0
for %%x in (%*) do Set /A argC+=1

if %argC% LEQ 1 echo "usage: $0 -image <img> [ -test <num> ] [ -log OFF|ALL|FINE ] [ -sleep <seconds> ]" & GOTO END

docker pull nunopreguica/sd2021-tester-tp1
docker run --rm --network=sdnet -it -v /var/run/docker.sock:/var/run/docker.sock nunopreguica/sd2021-tester-tp1 %*

:END
