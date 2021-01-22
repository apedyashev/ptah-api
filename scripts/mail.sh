#!/bin/sh
docker run -p 1080:1080 -p 1025:1025 maildev/maildev bin/maildev --base-pathname /maildev -w 1080 -s 1025