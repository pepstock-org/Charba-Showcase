# Charba Showcase GWT Web Toolkit

<p align="left">
  <a href="https://github.com/pepstock-org/Charba-Showcase/actions/workflows/build.yaml"><img alt="Build" src="https://github.com/pepstock-org/Charba-Showcase/workflows/Build/badge.svg?branch=master"></a>
  <a href="https://pepstock-org.github.io/Charba-Showcase/"><img alt="GWT showcase" src="https://img.shields.io/badge/Showcase-GWT-F27173.svg"></a>
</p>

This repository contains the source code of the **Charba** showcase site, based on GWT Web Toolkit, located [here](https://pepstock-org.github.io/Charba-Showcase/).

There are more than 220 samples about all **Charba** capabilities.

## Building

To build the project, the GWT (version 2.8.2) jar artifacts are provided into `lib` folder.

The project uses [Ant build.xml](https://github.com/pepstock-org/Charba-Showcase/blob/4.1/build.xml) and execute it with  `buildAll` target, as following:

```
ant buildAll
```

## Running

The showcase is deployed into `war` folder.

To run locally the showcase, open `war/index.html` file with the browser.
