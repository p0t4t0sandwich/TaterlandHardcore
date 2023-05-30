#!/bin/bash

VERSION=1.0.0
MC_VERSION=1.19.4
PROJ_NAME=tempbandeath

# Make directories
rm -rf ./temp_build
mkdir -p ./temp_build
cd temp_build

rm -rf ./$PROJ_NAME
mkdir -p ./$PROJ_NAME/ca/sperrer/p0t4t0sandwich/$PROJ_NAME

# Copy output Jars to temp directory
cp ../bukkit/build/libs/bukkit-*.jar ./
mv ./bukkit-*.jar ./bukkit.zip

cp ../bungee/build/libs/bungee-*.jar ./
mv ./bungee-*.jar ./bungee.zip

cp ../common/build/libs/common-*.jar ./
mv ./common-*.jar ./common.zip

cp ../fabric/build/libs/fabric-*.jar ./
mv ./fabric-*.jar ./fabric.zip

cp ../forge/build/libs/forge-*.jar ./
mv ./forge-*.jar ./forge.zip

cp ../velocity/build/libs/velocity-*.jar ./
mv ./velocity-*.jar ./velocity.zip

# Unzip Jars
unzip ./bukkit.zip -d ./bukkit
unzip ./bungee.zip -d ./bungee
unzip ./common.zip -d ./common
unzip ./fabric.zip -d ./fabric
unzip ./forge.zip -d ./forge
unzip ./velocity.zip -d ./velocity

# Process Jars
cp -r ./bukkit/ca/sperrer/p0t4t0sandwich/$PROJ_NAME/bukkit ./$PROJ_NAME/ca/sperrer/p0t4t0sandwich/$PROJ_NAME/bukkit
cp ./bukkit/plugin.yml ./$PROJ_NAME

#cp -r ./bungee/ca/sperrer/p0t4t0sandwich/$PROJ_NAME/bungee ./$PROJ_NAME/ca/sperrer/p0t4t0sandwich/$PROJ_NAME/bungee
#cp ./bungee/bungee.yml ./$PROJ_NAME

cp -r ./common/* ./$PROJ_NAME
rm -rf ./$PROJ_NAME/META-INF/*
rm -f ./$PROJ_NAME/LICENSE

#cp -r ./fabric/ca/sperrer/p0t4t0sandwich/$PROJ_NAME/fabric ./$PROJ_NAME/ca/sperrer/p0t4t0sandwich/$PROJ_NAME/fabric
#cp ./fabric/fabric.mod.json ./$PROJ_NAME
#cp ./fabric/$PROJ_NAME.mixins.json ./$PROJ_NAME
#cp -r ./fabric/assets ./$PROJ_NAME
#
#cp -r ./forge/ca/sperrer/p0t4t0sandwich/$PROJ_NAME/forge ./$PROJ_NAME/ca/sperrer/p0t4t0sandwich/$PROJ_NAME/forge
#cp ./forge/pack.mcmeta ./$PROJ_NAME
#cp ./forge/META-INF/mods.toml ./$PROJ_NAME/META-INF
#
#cp -r ./velocity/ca/sperrer/p0t4t0sandwich/$PROJ_NAME/velocity ./$PROJ_NAME/ca/sperrer/p0t4t0sandwich/$PROJ_NAME/velocity
#cp ./velocity/velocity.yml ./$PROJ_NAME
#cp ./velocity/velocity-plugin.json ./$PROJ_NAME

# Zip Jar contents
cd ./$PROJ_NAME
zip -r ./$PROJ_NAME.zip ./*

# Rename Jar
mv ./$PROJ_NAME.zip ../../build/libs/TempBanDeath-$VERSION-$MC_VERSION.jar
