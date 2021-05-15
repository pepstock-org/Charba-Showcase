# Charba Showcase GWT Web Toolkit

[![Build](https://github.com/pepstock-org/Charba-Showcase/workflows/Build/badge.svg?branch=master)](https://github.com/pepstock-org/Charba-Showcase/actions/workflows/build.yaml) [![ShowCase](https://img.shields.io/static/v1?message=ShowCase&color=informational)](https://pepstock-org.github.io/Charba-Showcase/)

This repository contains the source code of the **Charba** showcase site, based on GWT Web Toolkit, located [here](https://pepstock-org.github.io/Charba-Showcase/).

There are more than 180 samples about all **Charba** capabilities.

## Building

To build the project, the GWT (version 2.8.2) jar artifacts are provided into `lib` folder.

The project uses [Ant build.xml](https://github.com/pepstock-org/Charba-Showcase/blob/4.0/build.xml) and execute it with  `buildAll` target, as following:

```
ant buildAll
```

## Running

The showcase is deployed into `war` folder.

To run locally the showcase, open `war/index.html` file with the browser.
