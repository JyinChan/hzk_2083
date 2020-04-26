#!/usr/bin/env bash

pid=`ps aux|grep HServer|awk '{print $2}'`
echo ${pid}
kill ${pid}