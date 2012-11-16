#!/bin/sh
java -cp build/classes/:libs/jar/snakeyaml-1.10.jar:libs/jar/lwjgl.jar:libs/jar/slick.jar:libs/jar/lwjgl_util.jar -Djava.library.path=libs/native/linux de.stealmycode.beehive.Beehive
