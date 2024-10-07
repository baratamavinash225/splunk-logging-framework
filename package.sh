#!/bin/bash
#usage sh package.sh 0.0.1-SNAPSHOT
version=$1
sbt "publish"
cs bootstrap com.avinash:cef-splunk-logging-framework_2.12:${version} -f --assembly -o cef-splunk-logging-framework.jar